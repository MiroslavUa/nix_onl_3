package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.derivative.Derivative;
import com.kulbachniy.homeworks.derivative.DerivativeType;
import com.kulbachniy.homeworks.derivative.Exchange;
import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StockServiceOptional {
    private final StockRepository stockRepository;

    public StockServiceOptional(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public void update_ifPresent(String ticker){
        final Optional<Stock> stockOptional = Optional.ofNullable(stockRepository.findByTicker(ticker));
        stockOptional.ifPresent(stockRepository::update);
    }

    public void setExchange_orElse(Stock stock, Exchange name){
        Optional<Exchange> exchange = Optional.ofNullable(name);
        stock.setExchange(exchange.orElse(Exchange.NASDAQ));
        stockRepository.update(stock);
    }

    public DerivativeType getDerivativeType_orElseGet(Stock stock) {
        final Optional<DerivativeType> stockOptional = Optional.ofNullable(stock.getType());
        return stockOptional.orElseGet(() -> DerivativeType.STOCK);
    }

    public void updateIfPresent_orElse(Stock stock){
        final Optional<Stock> stockOptional = Optional.ofNullable(stockRepository.findByTicker(stock.getTicker()));
        stockOptional.ifPresentOrElse(stockRepository::update, () -> System.out.println("There is no "
                + stock.getTicker() + " in repository. First create it") );
    }

    public void ifExistUpdate_orSaveEmpty(Stock stock){
        String ticker = stock.getTicker();
        final Optional<Stock> stockOptional = Optional.ofNullable(stockRepository.findByTicker(ticker));
        final Optional<Stock> toBeSaved = stockOptional.or( () -> Optional.of(new Stock(ticker)));
        stockRepository.save(toBeSaved.get());
    }

    public void printStockInfo_orElseThrowException(Stock stock){
        String ticker = stock.getTicker();
        final Optional<Derivative> stockOptional = Optional.ofNullable(stockRepository.findByTicker(ticker));
        Stock stockToPrint = (Stock) stockOptional.orElseThrow( () ->
                new IllegalArgumentException("There is no " + ticker + " stock in current repository"));
        System.out.println(stockToPrint);
    }

    public void mapStringToStockAndSave(String ticker){
        Function<String, Stock> toStock = Stock::new;
        final Optional<String> tickerOptional = Optional.ofNullable(ticker);
        final Optional<Stock> stockOptional = tickerOptional.map(toStock);
        stockOptional.ifPresent(stockRepository::save);
    }

    public Optional<Stock> defineVolatility(Stock stock){
        final Optional<Stock> stockOptional = Optional.ofNullable(stock);
        Predicate<Stock> highVolatility = s -> { return s.getAverageTrueRange() > 5; };
        return stockOptional.filter(highVolatility);
    }
}