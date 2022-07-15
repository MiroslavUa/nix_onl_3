package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.derivative.Derivative;
import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public void save(Derivative stock) {
        stockRepository.save(stock);
    }

    public void saveAll(List<Derivative> derivatives) {stockRepository.saveAll(derivatives);}

    public void update(Derivative stock){
        stockRepository.update(stock);
    }

    public void delete(String id){
        stockRepository.delete(id);
    }

    public Derivative findByTicker(String ticker){
        return stockRepository.findByTicker(ticker);
    }

    public Derivative findById(String id){
        return stockRepository.findById(id);
    }

    public List<Derivative> getAll(){
        return stockRepository.getAll();
    }
}
