package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class StockServiceTest {

    private StockService target;
    private StockRepository stockRepository;

    private StockService targetSpy;
    private StockRepository stockRepositorySpy;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stockRepository = Mockito.mock(StockRepository.class);
        target = new StockService(stockRepository);

        stockRepositorySpy = Mockito.spy(new StockRepository());
        targetSpy = new StockService(stockRepositorySpy);

        stock = new Stock("BA", Exchange.NYSE, 125.5, "Boeing", "Aerospace Defense",
                12345612.5, 12.5, LocalDateTime.now());
    }

    @Test
    void save() {
        target.save(stock);
        ArgumentCaptor<Stock> argumentStock = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stockRepository).save(argumentStock.capture());
        Assertions.assertEquals("BA", argumentStock.getValue().getTicker());
    }

    @Test
    void saveNull() {
        target.save(null);
        ArgumentCaptor<Stock> argument = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stockRepository).save(argument.capture());
    }

    @Test
    void saveAll() {
        Stock firstStock = stock;
        Stock secondStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now());
        List<Stock> stocks = List.of(firstStock, secondStock);
        target.saveAll(stocks);
        Mockito.verify(stockRepository, times(1)).saveAll(stocks);
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
        target.update(stock);

        ArgumentCaptor<Stock> argumentStock = ArgumentCaptor.forClass((Stock.class));
        Mockito.verify(stockRepository).update(argumentStock.capture());
        Assertions.assertEquals(price, argumentStock.getValue().getPrice());
        Assertions.assertEquals(volume, argumentStock.getValue().getVolume());
        Assertions.assertEquals(atr, argumentStock.getValue().getAverageTrueRange());
        Assertions.assertEquals(date, argumentStock.getValue().getDate());
    }

    @Test
    void delete() {
        final String id = stock.getId();
        target.delete(id);
        ArgumentCaptor<Stock> argumentStock = ArgumentCaptor.forClass((Stock.class));
        Mockito.verify(stockRepository).delete(id);
    }

    @Test
    void delete_realMock(){
        target.save(stock);
        Mockito.when(stockRepository.delete(stock.getId())).thenCallRealMethod();
        assertEquals(0, target.getAll().size());
    }

    @Test
    void delete_realSpy(){
        targetSpy.save(stock);
        targetSpy.delete(stock.getId());
        Mockito.verify(stockRepositorySpy).delete(stock.getId());
        assertNull(targetSpy.findById(stock.getId()));
    }

    @Test
    void findByTicker() {
        target.save(stock);
        Stock otherStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now());
        target.save(otherStock);

        Stock result = (Stock) target.findByTicker(otherStock.getTicker());
        ArgumentCaptor<Stock> argumentStock = ArgumentCaptor.forClass((Stock.class));
        Mockito.verify(stockRepository, times(2)).save(argumentStock.capture());

        Mockito.verify(stockRepository, times(1)).findByTicker(argumentStock.getValue().getTicker());
        Assertions.assertEquals("LMT", argumentStock.getValue().getTicker());
    }

    @Test
    void findById() {
        target.save(stock);
        Stock otherStock = new Stock("LMT", Exchange.NYSE, 128.6,
                "Lockheed Martin", "Aerospace Defense", 123654987.2, 14.8,LocalDateTime.now());
        target.save(otherStock);

        Stock result = (Stock) target.findById(otherStock.getId());
        ArgumentCaptor<Stock> argumentStock = ArgumentCaptor.forClass((Stock.class));
        Mockito.verify(stockRepository, times(2)).save(argumentStock.capture());

        Mockito.verify(stockRepository, times(1)).findById(argumentStock.getValue().getId());
        Assertions.assertEquals(otherStock.getId(), argumentStock.getValue().getId());
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(stockRepository).getAll();
    }
}