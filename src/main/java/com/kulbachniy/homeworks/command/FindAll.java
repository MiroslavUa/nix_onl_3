package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.DerivativeService;
import com.kulbachniy.homeworks.service.FuturesService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.IOException;
import java.util.List;

public class FindAll implements Command{
    private static final DerivativeService<Stock> STOCK_SERVICE = StockService.getInstance();
    private static final DerivativeService<Futures> FUTURES_SERVICE = FuturesService.getInstance();

    @Override
    public void execute() throws IOException {
        List<Stock> stocks = STOCK_SERVICE.getAll();
        if (stocks.isEmpty()){
            System.out.println("Stock list is empty");
        } else {
            for (Stock s:stocks) {
                System.out.println("Stock list contains " + stocks.size() + " stocks.");
                System.out.println(s);
            }
        }

        List<Futures> futuresList = FUTURES_SERVICE.getAll();
        if(futuresList.isEmpty()){
            System.out.println("Futures list is empty");
        } else {
            for(Futures f:futuresList){
                System.out.println("Futures list contains " + futuresList.size() + " futures.");
                System.out.println(f);
            }
        }
    }
}
