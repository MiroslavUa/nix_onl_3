package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.CrudRepository;
import com.kulbachniy.homeworks.repository.FuturesRepository;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockService extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private final StockRepository repository;

    private static StockService instance;

    public StockService(StockRepository repository){
        super(repository);
        this.repository = repository;
    }

    public static StockService getInstance(){
        if(instance == null) {
            instance = new StockService(StockRepository.getInstance());
        }
        return instance;
    }
}
