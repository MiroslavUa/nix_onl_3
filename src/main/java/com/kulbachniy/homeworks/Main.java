package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.StockServiceStream;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //ProgramRun.run();
        Stock a = new Stock("A", Exchange.NYSE, 100.0, "Company A", "Aerospace Defence",
                1111111111, 10.0, LocalDateTime.parse("2022-07-01T00:00:01"), Collections.emptyList());
        Stock b = new Stock("B", Exchange.NYSE, 200.0, "Company B", "Aerospace Defence",
                222222222, 20.0, LocalDateTime.parse("2022-07-01T00:00:02"), Collections.emptyList());
        Stock c = new Stock("C", Exchange.NYSE, 100.0, "Company C", "Aerospace Defence",
                333333333, 30.0, LocalDateTime.parse("2022-07-01T00:00:04"), Collections.emptyList());
        Stock d = new Stock("D", Exchange.NYSE, 400.0, "Company D", "Aerospace Defence",
                444444444, 40.0, LocalDateTime.parse("2022-07-01T00:00:04"), Collections.emptyList());
        Stock aa = new Stock("AA", Exchange.NASDAQ, 1000.0, "Company AA", "Communication Services",
                555555555, 100.0, LocalDateTime.parse("2022-07-01T00:00:05"), Collections.emptyList());
        Stock bb = new Stock("BB", Exchange.NASDAQ, 2000.0, "Company BB", "Communication Services",
                666666666, 200.0, LocalDateTime.parse("2022-07-01T00:00:06"), Collections.emptyList());
        Stock cc = new Stock("CC", Exchange.NASDAQ, 4000.0, "Company CC", "Communication Services",
                777777777, 400.0, LocalDateTime.parse("2022-07-01T00:00:08"), Collections.emptyList());
        Stock dd = new Stock("DD", Exchange.NASDAQ, 8000.0, "Company CC", "Communication Services",
                888888888, 800.0, LocalDateTime.parse("2022-07-01T00:00:10"), Collections.emptyList());

        List<Stock> stocks = new ArrayList<>(){
            { add(b); add(a); add(cc);  add(aa); add(c); add(d); add(bb);  add(dd); add(a); add(a); }
        };

        final StockServiceStream STOCK_SERVICE_STREAM = StockServiceStream.getInstance();
        STOCK_SERVICE_STREAM.saveAll(stocks);

        //Найти товары дороже цены Х и показать их наименование
        System.out.print("Show all stocks with price greater than $30:");
        STOCK_SERVICE_STREAM.priceGreaterThen(stocks, 30);

        //Посчитать сумму товаров через reduce
        Double totalSum = STOCK_SERVICE_STREAM.getTotalPrice(stocks).isPresent()
                ? STOCK_SERVICE_STREAM.getTotalPrice(stocks).get()
                : 0;
        System.out.print("Total price of all stocks is:" + totalSum + "\n");

        //Отсортировать товары по названию, убрать дубликаты, преобразовать в Map где ключ это id товара, а значение это его тип
        System.out.print("Unsorted list with duplicates: " );
        stocks.stream()
                .map(Derivative::getTicker).forEach(t -> System.out.print(t + " "));
        System.out.print("\nSorted list with NO duplicates: " );
        STOCK_SERVICE_STREAM.sortNoDuplicates(stocks).stream()
                .map(Derivative::getTicker).forEach(t -> System.out.print(t + " "));

        Map<String, DerivativeType> map = STOCK_SERVICE_STREAM.toMap(stocks);
        map.forEach((k,v) -> System.out.println("Key:" + k + ", value: " + v));

        //Получить статистику по цене всех товаров
        STOCK_SERVICE_STREAM.showPriceStatistics(stocks);

        //Написать реализацию предиката который проверяет что в переданной коллекции у всех предметов есть цена.
        System.out.print("\nDo all stocks have price: " + STOCK_SERVICE_STREAM.allHavePrice(stocks));
        Stock lmt = new Stock("LMT");
        stocks.add(lmt);
        System.out.print("\nDo all stocks have price (LMT has NO price): " + STOCK_SERVICE_STREAM.allHavePrice(stocks));
        stocks.remove(lmt);
        System.out.println("\nDo all stocks have price (LMT was removed): " + STOCK_SERVICE_STREAM.allHavePrice(stocks));

        //Написать реализацию Function которая принимает Map<String, Object> и создает конкретный продукт на основании полей Map
        HashMap<String, Object> stockInfo = new HashMap<>();
        stockInfo.put("Ticker", "BA");
        stockInfo.put("Exchange", Exchange.NYSE);
        stockInfo.put("Price", 121.0);
        stockInfo.put("Company Name", "Boeing Co.");
        stockInfo.put("Industry", "Aerospace Defense");
        stockInfo.put("Volume", 158158158.0);
        stockInfo.put("ATR", 12.5);
        stockInfo.put("Date", LocalDateTime.now());
        stockInfo.put("Production", Collections.emptyList());

        Stock ba = STOCK_SERVICE_STREAM.createStockFromMap(stockInfo);
        stocks.add(ba);
        System.out.print("Stocks after creating new stock from HashMap: ");
        STOCK_SERVICE_STREAM.sortNoDuplicates(stocks).stream()
                .map(Derivative::getTicker).forEach(t -> System.out.print(t + " "));

        //Добавить в один товар коллекцию деталей (например List<String> details), проверить среди всех товаров есть ли наличие конкретной детали
        List<String> production = new ArrayList<>() {
            { add("Apache Longbow"); add("AV-8B Harrier II"); add("B-52 StratoFortress"); add("McDonnell Douglas F-15 Eagle"); }
        };
        ba.setProduction(production);

        boolean hasModel;
        hasModel = STOCK_SERVICE_STREAM.produceModel(stocks, "F-22");
        System.out.println("\nDoes any company of list produce F-22: " + hasModel);
        hasModel = STOCK_SERVICE_STREAM.produceModel(stocks, "Apache Longbow");
        System.out.println("Does any company of list produce Apache Longbow: " + hasModel);
    }
}
