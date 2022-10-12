package com.kulbachniy.homeworks.service.crudservice;

import com.kulbachniy.homeworks.model.Portfolio;
import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.repository.PortfolioRepository;
import com.kulbachniy.homeworks.repository.jdbc.PortfolioRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);
    protected final PortfolioRepository repository;
    private static PortfolioService instance;

    private PortfolioService(PortfolioRepository repository){
        this.repository = repository;
        LOGGER.info("Portfolio Service have been created");
    }

    public static PortfolioService getInstance(){
        if(instance == null) {
            instance = new PortfolioService(PortfolioRepositoryDb.getInstance());
        }
        return instance;
    }

    public void save(Portfolio portfolio){
        repository.save(portfolio);
    }

    public void saveAll(List<Portfolio> portfolios) {
        repository.saveAll(portfolios);
    }

    public void update(Portfolio portfolio){
        repository.update(portfolio);
    }

    public boolean delete(String id){
        return repository.delete(id);
    }

    public Portfolio findById(String id) {
        return repository.findById(id);
    }

    public List<Portfolio> getAll(){
        return repository.getAll();
    }

    public List<Portfolio> getExpensiveThan(double sum){return repository.getExpensiveThan(sum);}

    public int quantity(){return repository.quantity();}

    public void updateTime(Derivative derivative){repository.updateTime(derivative);}

    public Map<Double, List<Portfolio>> groupBySum(){return repository.groupBySum();}

}
