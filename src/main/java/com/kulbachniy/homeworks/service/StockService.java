package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockService extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

//    private final StockRepository stockRepository;

    public StockService(CrudRepository<Stock> repository){
        super(repository);
    }

//    public void save(Stock stock) {
//        stockRepository.save(stock);
//    }

//    public void saveAll(List<Stock> stocks) {stockRepository.saveAll(stocks);}

//    public void update(Stock stock){
//        stockRepository.update(stock);
//    }

//    public void delete(String id){
//        stockRepository.delete(id);
//    }

//    public Stock findByTicker(String ticker){
//        return stockRepository.findByTicker(ticker);
//    }

//    public Stock findById(String id){
//        return stockRepository.findById(id);
//    }

//    public List<Stock> getAll(){
//        return stockRepository.getAll();
//    }
}
