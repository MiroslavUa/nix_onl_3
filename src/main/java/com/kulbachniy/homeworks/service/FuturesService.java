package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuturesService extends DerivativeService<Futures> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuturesService.class);

    public FuturesService(CrudRepository<Futures> repository){
        super(repository);
    }
}
