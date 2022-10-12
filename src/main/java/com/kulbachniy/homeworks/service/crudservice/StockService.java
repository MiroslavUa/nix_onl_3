package com.kulbachniy.homeworks.service.crudservice;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.jdbc.StockRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class StockService extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private final StockRepositoryDb repository;

    private static StockService instance;

    @Autowired
    public StockService(StockRepositoryDb repository){
        super(repository);
        this.repository = repository;
        LOGGER.info("StockService have been created");
    }

    public static StockService getInstance(){
        if(instance == null) {
            instance = new StockService(StockRepositoryDb.getInstance());
        }
        return instance;
    }
}
