package com.kulbachniy.hw10.service;

import com.kulbachniy.hw10.derivative.Derivative;
import com.kulbachniy.hw10.derivative.Stock;
import com.kulbachniy.hw10.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StockService {
    StockRepository stockRepository = StockRepository.getInstance();

    public void create(Derivative stock){
        stockRepository.create(stock);
    }

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
