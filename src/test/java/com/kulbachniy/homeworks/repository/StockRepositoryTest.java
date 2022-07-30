package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class StockRepositoryTest {

    private StockRepository target;
    private Stock stock;

    private String id;

    @BeforeEach
    void setUp() {
        target = new StockRepository();
        stock = new Stock("BA", Exchange.NYSE, 125.5, "Boeing", "Aerospace Defense",
                12345612.5, 12.5, LocalDateTime.now(), production);
    }

    @Test
    void getInstance_repositoryExists() {
        StockRepository newRepository = StockRepository.getInstance();
        Assertions.assertEquals(newRepository, target);
    }

    @Test
    void getInstance_repositoryAbsent() {
        target = null;
        StockRepository newRepository = StockRepository.getInstance();
        Assertions.assertNotEquals(newRepository, target);
    }

    @Test
    void save() {
        target.save(stock);
        final List<Stock> stocks =  target.getAll();
        Assertions.assertEquals(1, stocks.size());
        Assertions.assertEquals(stocks.get(0).getId(), stock.getId());
    }

    @Test
    void saveNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Stock> resultList = target.getAll();
        Assertions.assertEquals(0, resultList.size());
    }

    @Test
    void saveAll_oneStock(){
        target.saveAll(Collections.singletonList(stock));
        final  List<Stock> stocks = target.getAll();
        Assertions.assertEquals(1, stocks.size());
        Assertions.assertEquals(stocks.get(0).getId(), stock.getId());
    }

    @Test
    void saveAll_noStock(){
        target.saveAll(Collections.emptyList());
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(0, stocks.size());
    }

    @Test
    void saveAll_manyStocks(){
        final Stock secondStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now(), production);
        final Stock thirdStock = new Stock("MSFT", Exchange.NASDAQ, 457.8,
                "Microsoft", "Software - Infrastructure ", 34564356.89, 7.49, LocalDateTime.now(), production);
        target.saveAll(List.of(stock, secondStock, thirdStock));
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(3, stocks.size());
        Assertions.assertEquals(stocks.get(0).getId(), stock.getId());
        Assertions.assertEquals(stocks.get(1).getId(), secondStock.getId());
        Assertions.assertEquals(stocks.get(2).getId(), thirdStock.getId());
    }

    @Test
    void saveAll_hasNull(){
        final List<Stock> stocks = new ArrayList<>();
        stocks.add(stock);
        stocks.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(stocks));
        final List<Stock> added = target.getAll();
        Assertions.assertEquals(1, added.size());
    }

    @Test
    void update() {
        final double price = 255.5;
        final double volume = 36535656;
        final double atr = 25.7;
        final LocalDateTime date = LocalDateTime.now();

        target.save(stock);
        stock.setPrice(price);
        stock.setVolume(volume);
        stock.setAverageTrueRange(atr);
        stock.setDate(date);

        final boolean isUpdated = target.update(stock);
        Assertions.assertTrue(isUpdated);

        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(1, stocks.size());

        Stock stockToCompare = (Stock) stocks.get(0);
        Assertions.assertEquals(price, stockToCompare.getPrice());
        Assertions.assertEquals(volume, stockToCompare.getVolume());
        Assertions.assertEquals(atr, stockToCompare.getAverageTrueRange());
        Assertions.assertEquals(date, stockToCompare.getDate());
        Assertions.assertEquals(stock.getId(), stockToCompare.getId());
    }

    @Test
    void update_noStock() {
        target.save(stock);
        final Stock absentStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now(), production);

        final boolean isUpdated = target.update(absentStock);
        Assertions.assertFalse(isUpdated);

        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(1, stocks.size());
        Assertions.assertEquals(stock.getId(), stocks.get(0).getId());
    }

    @Test
    void delete() {
        target.save(stock);
        final boolean isDeleted = target.delete(stock.getId());
        Assertions.assertTrue(isDeleted);
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(0, stocks.size());
    }

    @Test
    void delete_noStock() {
        target.save(stock);
        final Stock  absentStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now(), production);
        final boolean isDeleted = target.delete(absentStock.getId());
        Assertions.assertFalse(isDeleted);
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(1, stocks.size());
    }

    @Test
    void findById() {
        target.save(stock);
        final Derivative result = target.findById(stock.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stock.getId(), result.getId());
    }

    @Test
    void findById_noStock() {
        target.save(stock);
        final String randomId = UUID.randomUUID().toString();
        final Derivative result = target.findById(randomId);
        Assertions.assertNull(result);
    }

    @Test
    void findByTicker() {
        target.save(stock);
        final Derivative result = target.findByTicker(stock.getTicker());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stock.getTicker(), result.getTicker());
        Assertions.assertEquals(stock.getId(), result.getId());
    }

    @Test
    void findByTicker_noStock() {
        target.save(stock);
        final String ticker = "ABC";
        final Derivative result = target.findByTicker(ticker);
        Assertions.assertNull(result);
    }

    @Test
    void getAll() {
        target.save(stock);
        Stock otherStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now(), production);
        target.save(otherStock);
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(2, stocks.size());
    }

    @Test
    void getAll_noStocks() {
        final List<Stock> stocks = target.getAll();
        Assertions.assertEquals(0, stocks.size());
    }
}