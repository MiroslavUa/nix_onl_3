package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.model.Portfolio;
import com.kulbachniy.homeworks.model.derivative.*;
import com.kulbachniy.homeworks.service.crudservice.CurrencyPairService;
import com.kulbachniy.homeworks.service.crudservice.FuturesService;
import com.kulbachniy.homeworks.service.crudservice.PortfolioService;
import com.kulbachniy.homeworks.service.crudservice.StockService;
import lombok.SneakyThrows;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        CurrencyPair pair1 = new CurrencyPair("USDEUR", DerivativeType.CURRENCY_PAIR,
                Exchange.FX, 100d, "USD", "EUR", LocalDateTime.now());
        CurrencyPair pair2 = new CurrencyPair("EURCHF", DerivativeType.CURRENCY_PAIR,
                Exchange.FX, 200d, "EUR", "CHF", LocalDateTime.now());
        CurrencyPair pair3 = new CurrencyPair("GBPUSD", DerivativeType.CURRENCY_PAIR,
                Exchange.FX, 300d, "GBP", "USD", LocalDateTime.now());
        CurrencyPair pair4 = new CurrencyPair("EURJPY", DerivativeType.CURRENCY_PAIR,
                Exchange.FX, 400d, "EUR", "JPY", LocalDateTime.now());
        CurrencyPair pair5 = new CurrencyPair("GBPAUD", DerivativeType.CURRENCY_PAIR,
                Exchange.FX, 500d, "GBPAUD", "AUD", LocalDateTime.now());

        CurrencyPairService currencyPairService = CurrencyPairService.getInstance();
        currencyPairService.saveAll(List.of(pair1, pair2, pair3, pair4, pair5));

        Stock stock1 = new Stock("A", Exchange.NYSE, 100d, "Company A",
                "Aerospace Defense", 123456789d, 11.0d, LocalDateTime.now(), Collections.emptyList());
        Stock stock2 = new Stock("B", Exchange.NYSE, 200d, "Company B",
                "Aerospace Defense", 123456789d, 15.0d, LocalDateTime.now(), Collections.emptyList());
        Stock stock3 = new Stock("C", Exchange.NYSE, 300d, "Company C",
                "Aerospace Defense", 123456789d, 16.0d, LocalDateTime.now(), Collections.emptyList());
        Stock stock4 = new Stock("D", Exchange.NYSE, 400d, "Company D",
                "Aerospace Defense", 123456789d, 18.0d, LocalDateTime.now(), Collections.emptyList());
        Stock stock5 = new Stock("E", Exchange.NYSE, 500d, "Company E",
                "Aerospace Defense", 123456789d, 19.0d, LocalDateTime.now(), Collections.emptyList());

        StockService stockService = StockService.getInstance();
        stockService.saveAll(List.of(stock1, stock2, stock3, stock4, stock5));

        Futures futures1 = new Futures("G", DerivativeType.FUTURES, Exchange.CME, 100.d, "Gold", LocalDateTime.now());
        Futures futures2 = new Futures("S", DerivativeType.FUTURES, Exchange.CME, 200.d, "Silver", LocalDateTime.now());
        Futures futures3 = new Futures("P", DerivativeType.FUTURES, Exchange.CME, 300.d, "Platinum", LocalDateTime.now());
        Futures futures4 = new Futures("OL", DerivativeType.FUTURES, Exchange.CME, 400.d, "Light", LocalDateTime.now());
        Futures futures5 = new Futures("OB", DerivativeType.FUTURES, Exchange.CME, 500.d, "Brent", LocalDateTime.now());

        FuturesService futuresService = FuturesService.getInstance();
        futuresService.saveAll(List.of(futures1, futures2, futures3, futures4, futures5));

        Portfolio portfolio1 = new Portfolio();
        portfolio1.addDerivatives(List.of(stock1, pair1, futures1));

        Portfolio portfolio2 = new Portfolio();
        portfolio2.addDerivatives(List.of(stock2, pair2, futures2));

        Portfolio portfolio3 = new Portfolio();
        portfolio3.addDerivatives(List.of(stock3, pair3, futures3));

        Portfolio portfolio4 = new Portfolio();
        portfolio4.addDerivative(futures3);

        List<Portfolio> portfolios = List.of(portfolio1, portfolio2, portfolio3, portfolio4);

        PortfolioService portfolioService = PortfolioService.getInstance();
        portfolioService.saveAll(portfolios);

        List<Portfolio> expensiveThan = portfolioService.getExpensiveThan(300);
        expensiveThan.forEach(System.out::println);

        System.out.println(portfolioService.quantity());

        Thread.sleep(10000);
        portfolioService.updateTime(stock1);
        portfolioService.updateTime(stock2);
        portfolioService.updateTime(stock3);
        System.out.println(portfolioService.getAll());

        Map<Double, List<Portfolio>> listMap = portfolioService.groupBySum();
        listMap.entrySet().forEach(System.out::println);

    }
}
