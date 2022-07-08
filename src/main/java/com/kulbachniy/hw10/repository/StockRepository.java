package com.kulbachniy.hw10.repository;

import com.kulbachniy.hw10.derivative.Derivative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockRepository implements CrudRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(StockRepository.class);

    private final List<Derivative> stocks = new ArrayList<>();

    private static StockRepository instance;

    private StockRepository() {
        System.out.println("Stock Repository have been created");
    }

    public static StockRepository getInstance(){
        if(instance == null) {
            instance = new StockRepository();
        }
        return instance;
    }


    @Override
    public void create(Derivative derivative) {
        if(derivative == null){
            LOGGER.info("derivative cannot be null value");
        } else {
            LOGGER.info(derivative.getId() + " has been created");
            stocks.add(derivative);
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
}
