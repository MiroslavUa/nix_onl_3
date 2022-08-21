package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.repository.CrudRepository;
import com.kulbachniy.homeworks.repository.FuturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class FuturesService extends DerivativeService<Futures> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuturesService.class);

    private final FuturesRepository repository;

    private static FuturesService instance;

    @Autowired
    private FuturesService(FuturesRepository repository){
        super(repository);
        this.repository = repository;
        LOGGER.info("FuturesService have been created");
    }

    public static FuturesService getInstance(){
        if(instance == null) {
            instance = new FuturesService(FuturesRepository.getInstance());
        }
        return instance;
    }
}
