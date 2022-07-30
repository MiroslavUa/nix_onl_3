package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StockServiceStream extends DerivativeService<Stock>{
    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceStream.class);

    private final StockRepository repository;

    private static StockServiceStream instance;


    public StockServiceStream(StockRepository repository){
        super(repository);
        this.repository = repository;
    }

    public static StockServiceStream getInstance(){
        if(instance == null) {
            instance = new StockServiceStream(StockRepository.getInstance());
        }
        return instance;
    }

    //Найти товары дороже цены Х и показать их наименование
    public void priceGreaterThen(List<Stock> stocks, double price){
        stocks.stream()
                .filter(s -> s.getPrice()>price)
                .map(Derivative::getTicker)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
    }

    //Посчитать сумму товаров через reduce
    public Optional<Double> getTotalPrice(List<Stock> stocks){
        return stocks.stream()
                .map(Derivative::getPrice)
                .reduce(Double::sum);
    }

    //Отсортировать товары по названию, убрать дубликаты,
    public List<Stock> sortNoDuplicates(List<Stock> stocks) {
        return stocks.stream()
                .sorted(Comparator.comparing(Derivative::getTicker))
                .distinct()
                .toList();
    }

    // преобразовать в Map где ключ это id товара, а значение это его тип
    public Map<String, DerivativeType> toMap (List<Stock> stocks) {
        List<Stock> sorted = sortNoDuplicates(stocks);
        return sorted.stream()
                .collect(Collectors.toMap(Derivative::getId, Derivative::getType));
    }

    //Добавить в один товар коллекцию деталей (например List<String> details), проверить среди всех товаров есть ли наличие конкретной детали
    public boolean produceModel(List<Stock> stocks, String model){
        return stocks.stream()
                .flatMap(x -> x.getProduction().stream())
                .anyMatch(m -> m.equals(model));
    }

    //Получить статистику по цене всех товаров
    public DoubleSummaryStatistics getStatistics(List<Stock> stocks){
       return stocks.stream()
               .mapToDouble(Derivative::getPrice)
               .summaryStatistics();
    }

    public void showPriceStatistics(List<Stock> stocks){
        DoubleSummaryStatistics statistics = getStatistics(stocks);
        System.out.println("Total stocks: " + statistics.getCount());
        System.out.println("Average price: " + statistics.getAverage());
        System.out.println("Max price: " + statistics.getMax());
        System.out.println("Min price: " + statistics.getMin());
        System.out.println("Total sum: " + statistics.getSum());
    }

    //Написать реализацию предиката который проверяет что в переданной коллекции у всех предметов есть цена.
    private final Predicate<List<Stock>> allWithPrice = col -> col.stream().allMatch(s -> s.getPrice() != null);

    public boolean allHavePrice(List<Stock> stocks){
        return allWithPrice.test(stocks);
    }

    //Написать реализацию Function которая принимает Map<String, Object> и создает конкретный продукт на основании полей Map
    private final Function<Map<String, Object>, Stock> createStock = hashMap -> {
        return new Stock(
                (String) hashMap.get("Ticker"),
                (Exchange) hashMap.get("Exchange"),
                (Double) hashMap.get("Price"),
                (String) hashMap.get("Company Name"),
                (String) hashMap.get("Industry"),
                (Double) hashMap.get("Volume"),
                (Double) hashMap.get("ATR"),
                (LocalDateTime) hashMap.get("Date"),
                (List<String>) hashMap.get("Production"));
    };

    public Stock createStockFromMap(HashMap<String, Object> hashMap) {
        return createStock.apply(hashMap);
    }

}
