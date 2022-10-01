package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.crudservice.DerivativeService;
import com.kulbachniy.homeworks.service.crudservice.FuturesService;
import com.kulbachniy.homeworks.service.crudservice.StockService;

import java.io.IOException;

public class Delete implements Command{
    private static final DerivativeService<Stock> STOCK_SERVICE = StockService.getInstance();
    private static final DerivativeService<Futures> FUTURES_SERVICE = FuturesService.getInstance();

    @Override
    public void execute() throws IOException {
        System.out.println("Enter ticker to delete: ");

        final String input = UserInputUtil.stringValue().toUpperCase();
        Stock stock;
        Futures futures;
        Derivative result;

        stock = STOCK_SERVICE.findByTicker(input);
        futures = FUTURES_SERVICE.findByTicker(input);

        if (stock != null) {
            result = stock;
        } else if (futures != null) {
            result = futures;
        } else {
            result = null;
        }

        DerivativeType type;
        if(result == null) {
            System.out.println("There is no such ticker in repositories.");
            return;
        } else {
            type = result.getType();
        }

        double val;
        switch (type) {
            case STOCK -> {
                STOCK_SERVICE.delete(stock.getTicker());
            }
            case FUTURES -> {
                FUTURES_SERVICE.delete(futures.getTicker());
            }
        }
    }
}
