package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.model.derivative.CurrencyPair;
import com.kulbachniy.homeworks.repository.jdbc.CurrencyPairRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyPairService extends DerivativeService<CurrencyPair>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyPairService.class);

    private final CurrencyPairRepositoryDb repository;

    private static CurrencyPairService instance;

    @Autowired
    public CurrencyPairService(CurrencyPairRepositoryDb repository){
        super(repository);
        this.repository = repository;
        LOGGER.info("CurrencyPairService have been created");
    }

    public static CurrencyPairService getInstance(){
        if(instance == null) {
            instance = new CurrencyPairService(CurrencyPairRepositoryDb.getInstance());
        }
        return instance;
    }
}
