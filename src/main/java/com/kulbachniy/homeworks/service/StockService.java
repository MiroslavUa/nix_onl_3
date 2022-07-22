package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockService extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    public StockService(CrudRepository<Stock> repository){
        super(repository);
    }
}
