package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.derivative.Derivative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockRepository implements CrudRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(StockRepository.class);

    private final List<Derivative> stocks = new ArrayList<>();

    private static StockRepository instance;

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
    public void save(Derivative derivative) {
        if(derivative == null){
            final IllegalArgumentException exception = new IllegalArgumentException("Derivative must not be a null");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            Derivative foundDerivative = findByTicker(derivative.getTicker());
            if (foundDerivative == null) {
                LOGGER.info(derivative.getId() + " has been created");
                stocks.add(derivative);
            } else {
                LOGGER.info(derivative.getId() + " derivative already exists.");
                update(derivative);
            }
        }
    }

    @Override
    public void saveAll(List<Derivative> derivatives){
        for (Derivative derivative : derivatives){
            save(derivative);
        }
    }

    @Override
    public boolean update(Derivative derivative) {
        for(Derivative d: stocks){
            if(d.getId().equals(derivative.getId())){
                stocks.remove(d);
                stocks.add(derivative);
                LOGGER.info(d.getId() + " has been updated");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(Derivative d: stocks){
            if(d.getId().equals(id)){
                stocks.remove(d);
                LOGGER.info(d.getId() + " has been deleted");
                return true;
            }
        }
        return false;
    }

    @Override
    public Derivative findById(String id) {
        Derivative derivative = null;
        for(Derivative d: stocks){
            if(d.getId().equals(id)){
                derivative = d;
            }
        }
        return derivative;
    }

    @Override
    public Derivative findByTicker(String ticker) {
        Derivative derivative = null;
        for(Derivative d: stocks){
            if(d.getTicker().equals(ticker)){
                derivative = d;
            }
        }
        return derivative;
    }

    @Override
    public List<Derivative> getAll() {
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
}
