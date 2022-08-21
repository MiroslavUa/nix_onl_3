package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Singleton
public class FuturesRepository implements CrudRepository<Futures> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuturesRepository.class);

    private final List<Futures> futuresList = new ArrayList<>();

    private static FuturesRepository instance;

    public FuturesRepository() {
        LOGGER.info("Futures Repository have been created");
    }

    public static FuturesRepository getInstance(){
        if(instance == null) {
            instance = new FuturesRepository();
        }
        return instance;
    }

    @Override
    public void save(Futures futures) {
        if(futures == null){
            final IllegalArgumentException exception = new IllegalArgumentException("Derivative must not be a null");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            Futures foundFutures = findByTicker(futures.getTicker());
            if (foundFutures == null) {
                LOGGER.info(futures.getId() + " has been created");
                futuresList.add(futures);
            } else {
                LOGGER.info(futures.getId() + " derivative already exists.");
                update(futures);
            }
        }
    }

    @Override
    public void saveAll(List<Futures> futuresList){
        for (Futures futures : futuresList){
            save(futures);
        }
    }

    @Override
    public boolean update(Futures futures) {
        for(Futures f: futuresList){
            if(f.getId().equals(futures.getId())){
                futuresList.remove(f);
                futuresList.add(futures);
                LOGGER.info(f.getId() + " has been updated");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(Futures f: futuresList){
            if(f.getId().equals(id)){
                futuresList.remove(f);
                LOGGER.info(f.getId() + " has been deleted");
                return true;
            }
        }
        return false;
    }

    @Override
    public Futures findById(String id) {
        Futures futures = null;
        for(Futures f: futuresList){
            if(f.getId().equals(id)){
                futures = f;
            }
        }
        return futures;
    }

    @Override
    public Futures findByTicker(String ticker) {
        Futures futures = null;
        for(Futures f: futuresList){
            if(f.getTicker().equals(ticker)){
                futures = f;
            }
        }
        return futures;
    }

    @Override
    public List<Futures> getAll() {
        if (futuresList.isEmpty()) {
            return Collections.emptyList();
        }
        return futuresList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FuturesRepository)) return false;
        FuturesRepository that = (FuturesRepository) o;
        return futuresList.equals(that.futuresList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(futuresList);
    }
}
