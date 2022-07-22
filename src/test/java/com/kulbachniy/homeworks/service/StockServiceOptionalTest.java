package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.derivative.DerivativeType;
import com.kulbachniy.homeworks.derivative.Exchange;
import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceOptionalTest {

    private StockServiceOptional target;

    private StockRepository repository;

    private Stock stock;
    private Stock otherStock;

    @BeforeEach
    void setUp() {
       repository = new StockRepository();

       target = new StockServiceOptional(repository);
       stock = new Stock("BA", Exchange.NYSE, 125.5, "Boeing", "Aerospace Defense",
                12345612.5, 12.5, LocalDateTime.now());
       otherStock = new Stock("LMT", Exchange.NYSE, 128.6,  "Lockheed Martin", "Aerospace Defense",
               123654987.2, 14.8, LocalDateTime.now());
    }

    @Test
    void update_ifPresent_present() {
        repository.save(stock);
        Assertions.assertEquals(125.5, repository.findByTicker(stock.getTicker()).getPrice());

        stock.setPrice(145.5);
        target.update_ifPresent("BA");
        Assertions.assertEquals(145.5, repository.findByTicker(stock.getTicker()).getPrice());
    }

    @Test
    void update_ifPresent_absent() {
        stock.setPrice(145.5);
        target.update_ifPresent("BA");
        Assertions.assertNull(repository.findByTicker(stock.getTicker()));
    }

    @Test
    void setExchange_orElse_withExchange() {
        repository.save(stock);
        Exchange exchange = Exchange.AMEX;
        target.setExchange_orElse(stock, exchange);
        Assertions.assertEquals(Exchange.AMEX, repository.findByTicker(stock.getTicker()).getExchange());
    }

    @Test
    void setExchange_orElse_noExchange() {
        repository.save(stock);
        target.setExchange_orElse(stock, null);
        Assertions.assertEquals(Exchange.NASDAQ, repository.findByTicker(stock.getTicker()).getExchange());
    }

    @Test
    void getDerivativeType_orElseGet_withType() {
        repository.save(stock);
        Assertions.assertEquals(DerivativeType.STOCK, target.getDerivativeType_orElseGet(stock));
    }

    @Test
    void getDerivativeType_orElseGet_noType() {
        repository.save(stock);
        stock.setType(null);
        repository.update(stock);
        Assertions.assertEquals(DerivativeType.STOCK, target.getDerivativeType_orElseGet(stock));
    }

    @Test
    void updateIfPresent_orElse_stock() {
        repository.save(stock);
        stock.setPrice(500);
        target.updateIfPresent_orElse(stock);

        repository.save(otherStock);
        otherStock.setPrice(1000);
        target.updateIfPresent_orElse(otherStock);

        Assertions.assertEquals(500, repository.findByTicker("BA").getPrice());
        Assertions.assertEquals(1000, repository.findByTicker("LMT").getPrice());
    }

    @Test
    void updateIfPresent_orElse_noStock() {
        stock.setPrice(500);
        target.updateIfPresent_orElse(stock);

        otherStock.setPrice(1000);
        target.updateIfPresent_orElse(otherStock);
    }

    @Test
    void ifExistUpdate_orSaveEmpty_exist() {
        repository.save(stock);
        Assertions.assertEquals(125.5, repository.findByTicker("BA").getPrice());
        stock.setPrice(250.5);
        target.ifExistUpdate_orSaveEmpty(stock);
        Assertions.assertEquals(250.5, repository.findByTicker("BA").getPrice());
    }

    @Test
    void ifExistUpdate_orSaveEmpty_absent() {
        stock.setPrice(250.5);
        target.ifExistUpdate_orSaveEmpty(stock);
        Assertions.assertEquals("BA", repository.findByTicker("BA").getTicker());
        assertNull(repository.findByTicker("BA").getPrice());
        assertNull(repository.findByTicker("BA").getExchange());
        assertNull(repository.findByTicker("BA").getType());
        assertNull(repository.findByTicker("BA").getPrice());
        assertNotNull(repository.findByTicker("BA").getId());
    }


    @Test
    void printStockInfo_orElseThrowException_exist() throws IllegalArgumentException{
        repository.save(stock);
        target.printStockInfo_orElseThrowException(stock);
    }

    @Test
    void printStockInfo_orElseThrowException_absent() throws IllegalArgumentException {
        repository.save(stock);
        try{
        target.printStockInfo_orElseThrowException(otherStock);
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void mapStringToStockAndSave_ticker() {
        String microsoft = "MSFT";
        String generalDynamics = "GD";

        target.mapStringToStockAndSave(microsoft);
        target.mapStringToStockAndSave(generalDynamics);
        assertNull(repository.findByTicker(microsoft).getPrice());
        assertNull(repository.findByTicker(generalDynamics).getPrice());
        Assertions.assertEquals(2, repository.getAll().size());
    }

    @Test
    void mapStringToStockAndSave_noTicker() {
        String microsoft = null;
        target.mapStringToStockAndSave(microsoft);
        Assertions.assertEquals(0, repository.getAll().size());
    }

    @Test
    void defineVolatility_highVolatility() {
        Optional<Stock> stockOptional = target.defineVolatility(stock);
        Assertions.assertTrue(stockOptional.isPresent());
        Assertions.assertTrue(stockOptional.get().getAverageTrueRange() > 10);
    }

    @Test
    void defineVolatility_lowVolatility() {
        stock.setAverageTrueRange(4.99);
        Optional<Stock> stockOptional = target.defineVolatility(stock);
        Assertions.assertTrue(stockOptional.isEmpty());
    }
}