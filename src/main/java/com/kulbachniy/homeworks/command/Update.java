package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.crudservice.DerivativeService;
import com.kulbachniy.homeworks.service.crudservice.FuturesService;
import com.kulbachniy.homeworks.service.crudservice.StockService;

import java.io.IOException;
import java.time.LocalDateTime;

public class Update implements Command{
    private static final DerivativeService<Stock> STOCK_SERVICE = StockService.getInstance();
    private static final DerivativeService<Futures> FUTURES_SERVICE = FuturesService.getInstance();

    @Override
    public void execute() throws IOException {
        System.out.println("Enter ticker to update: ");

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
                System.out.println("Enter price: ");
                val = UserInputUtil.doubleValue();
                stock.setPrice(val);
                System.out.println("Enter volume: ");
                val = UserInputUtil.doubleValue();
                stock.setVolume(val);
                System.out.println("Enter Average True Range: ");
                val = UserInputUtil.doubleValue();
                stock.setAverageTrueRange(val);
                stock.setDate(LocalDateTime.now());
                STOCK_SERVICE.update(stock);
            }
            case FUTURES -> {
                System.out.println("Enter price: ");
                val = UserInputUtil.doubleValue();
                futures.setPrice(val);
                System.out.println("Enter expiration date: ");
                LocalDateTime date = UserInputUtil.dateValue();
                futures.setExpirationDate(date);
                FUTURES_SERVICE.update(futures);
            }
        }
    }
}