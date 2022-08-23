package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.CrudRepository;
import com.kulbachniy.homeworks.repository.FuturesRepository;
import com.kulbachniy.homeworks.repository.StockRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class StockService extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private final StockRepository repository;

    private static StockService instance;

    @Autowired
    public StockService(StockRepository repository){
        super(repository);
        this.repository = repository;
        LOGGER.info("StockService have been created");
    }

    public static StockService getInstance(){
        if(instance == null) {
            instance = new StockService(StockRepository.getInstance());
        }
        return instance;
    }
}
