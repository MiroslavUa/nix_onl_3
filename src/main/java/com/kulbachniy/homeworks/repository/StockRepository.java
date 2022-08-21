package com.kulbachniy.homeworks.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class StockRepository implements CrudRepository<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockRepository.class);

    private final List<Stock> stocks = new ArrayList<>();

    private static StockRepository instance;

    @Autowired
    public StockRepository() {
        System.out.println("Stock Repository have been created");
    }

    public static StockRepository getInstance(){
        if(instance == null) {
            instance = new StockRepository();
        }
        return instance;
    }


    @Override
    public void save(Stock stock) {
        if(stock == null){
            final IllegalArgumentException exception = new IllegalArgumentException("Derivative must not be a null");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            Stock foundStock = findByTicker(stock.getTicker());
            if (foundStock == null) {
                LOGGER.info(stock.getId() + " has been created");
                stocks.add(stock);
            } else {
                LOGGER.info(stock.getId() + " derivative already exists.");
                update(stock);
            }
        }
    }

    @Override
    public void saveAll(List<Stock> stocks){
        for (Stock stock : stocks){
            save(stock);
        }
    }

    @Override
    public boolean update(Stock stock) {
        for(Stock s: stocks){
            if(s.getId().equals(stock.getId())){
                stocks.remove(s);
                stocks.add(stock);
                LOGGER.info(s.getId() + " has been updated");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(Stock s: stocks){
            if(s.getId().equals(id)){
                stocks.remove(s);
                LOGGER.info(s.getId() + " has been deleted");
                return true;
            }
        }
        return false;
    }

    @Override
    public Stock findById(String id) {
        Stock stock = null;
        for(Stock s: stocks){
            if(s.getId().equals(id)){
                stock = s;
            }
        }
        return stock;
    }

    @Override
    public Stock findByTicker(String ticker) {
        Stock stock = null;
        for(Stock s: stocks){
            if(s.getTicker().equals(ticker)){
                stock = s;
            }
        }
        return stock;
    }

    @Override
    public List<Stock> getAll() {
        if (stocks.isEmpty()) {
            return Collections.emptyList();
        }
        return stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockRepository)) return false;
        StockRepository that = (StockRepository) o;
        return stocks.equals(that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stocks);
    }

    public static void resetInstance(){
       instance = null;
    }
}
