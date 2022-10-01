package com.kulbachniy.homeworks.service.crudservice;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class DerivativeService<T extends Derivative> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerivativeService.class);
    protected final CrudRepository<T> repository;

    protected DerivativeService(CrudRepository<T> repository){
        this.repository = repository;
    }

    public void save(T derivative){
        repository.save(derivative);
    }

    public void saveAll(List<T> derivatives) {
        repository.saveAll(derivatives);
    }

    public void update(T derivative){
        repository.update(derivative);
    }

    public void delete(String id){
        repository.delete(id);
    }

    public T findByTicker(String ticker){
        return repository.findByTicker(ticker);
    }

    public T findById(String id) {
        return repository.findById(id);
    }

    public List<T> getAll(){
        return repository.getAll();
    }

}
