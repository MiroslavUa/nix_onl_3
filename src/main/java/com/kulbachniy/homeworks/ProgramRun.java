package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.derivative.Derivative;
import com.kulbachniy.homeworks.derivative.Exchange;
import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import com.kulbachniy.homeworks.service.StockService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class ProgramRun {
    private static final StockService stockService = new StockService(StockRepository.getInstance());

    public static void run() {
        showMenu();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            boolean exit = false;

            while (!exit) {
                String choice = reader.readLine();

                switch (choice) {
                    case "1" -> {
                        save(reader);
                        showMenu();
                    }
                    case "2" -> {
                        update(reader);
                        showMenu();
                    }
                    case "3" -> {
                        delete(reader);
                        showMenu();
                    }
                    case "4" -> {
                        findByTicker(reader);
                        showMenu();
                    }
                    case "5" -> {
                        findById(reader);
                        showMenu();
                    }
                    case "6" -> {
                        findAll();
                        showMenu();
                    }
                    case "0" -> {
                        exit = true;
                        System.out.println("Execution finished...");
                    }
                    default -> {
                        System.out.println("Please, choose correct menu item – 0 - 6.\nTry again...");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showMenu(){
        System.out.printf("%s","1 – Save derivative.");
        System.out.printf("%30s","2 - Update derivative.");
        System.out.printf("%27s","3 — Delete derivative.");
        System.out.printf("%s","\n4 — Find derivative by ticker.");
        System.out.printf("%26s","5 — Find derivative by ID.");
        System.out.printf("%26s","6 — Find all derivatives.");
        System.out.printf("\n0 — Exit.");
        System.out.print("\nPlease, choose an operations: ");
    }

    private static void save(BufferedReader reader) throws IOException {
        Stock stock = newStock(reader);
        stockService.save(stock);
    }

    private static void update(BufferedReader reader) throws IOException{
        System.out.print("Enter stock ticker to update: ");
        String ticker = reader.readLine().toUpperCase();
        Stock stock = (Stock) stockService.findByTicker(ticker);
        if (stock == null) {
            System.out.print("There is no " + ticker + " in base.");
            return;
        } else {
            Stock updated = newStock(reader);
            stockService.delete(stock.getId());
            stockService.update(updated);
        }
    }

    private static void delete(BufferedReader reader) throws IOException{
        System.out.print("Enter stock id: ");
        String id = reader.readLine();
        Stock stock = (Stock) stockService.findById(id);
        if (stock != null) {
            stockService.delete(stock.getId());
        }
    }

    private static void findByTicker(BufferedReader reader) throws IOException{
        System.out.print("Enter stock ticker: ");
        String ticker = reader.readLine().toUpperCase();
        Stock stock = (Stock) stockService.findByTicker(ticker);
        if (stock != null) {
            System.out.println(stock);
        } else {
            System.out.println("There is not " + ticker + " in base");
        }
    }

    private static void findById(BufferedReader reader) throws IOException {
        System.out.print("Enter stock ID: ");
        String id = reader.readLine();
        Stock foundStock = (Stock) stockService.findById(id);
        System.out.println(foundStock.toString());
    }
    private static void findAll(){
        System.out.println("All stocks:");
        List<Stock> stocks = stockService.getAll();
        stocks.forEach(System.out::println);
    }

    private static Stock newStock(BufferedReader reader) throws IOException {
        System.out.print("Enter stock ticker: ");
        String ticker = reader.readLine().toUpperCase();

        System.out.print("Choose exchange: 1 - nyse, 2 - nasdaq, 3 - amex, 4 - cme, 5 - lse, 6 - fx: ");
        String market = reader.readLine();
        Exchange exchange = null;
        if (market.equals("1")){
            exchange = Exchange.NYSE;
        } else if (market.equals("2")) {
            exchange = Exchange.NASDAQ;
        } else if (market.equals("3")) {
            exchange = Exchange.AMEX;
        } else if (market.equals("4")) {
            exchange = Exchange.CME;
        } else if (market.equals("5")) {
            exchange = Exchange.LSE;
        } else if (market.equals("6")) {
            exchange = Exchange.FX;
        }

        System.out.print("Enter current price: ");
        String currentPrice = reader.readLine();
        double price = Double.parseDouble(currentPrice);

        System.out.print("Enter company name: ");
        String companyName = reader.readLine();

        System.out.print("Enter industry name: ");
        String industry = reader.readLine();

        System.out.print("Enter volume: ");
        double volume = Double.parseDouble(reader.readLine());

        System.out.print("Enter Average True Range: ");
        double atr = Double.parseDouble(reader.readLine());

        System.out.print("Enter date: ");
        LocalDateTime date = LocalDateTime.now();

        Stock stock = new Stock(ticker, exchange, price, companyName, industry, volume, atr, date);
        return stock;
    }
}
