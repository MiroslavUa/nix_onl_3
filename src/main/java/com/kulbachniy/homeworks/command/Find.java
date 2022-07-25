package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.DerivativeService;
import com.kulbachniy.homeworks.service.FuturesService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Find implements Command{
    private static final DerivativeService<Stock> STOCK_SERVICE = StockService.getInstance();
    private static final DerivativeService<Futures> FUTURES_SERVICE = FuturesService.getInstance();

    @Override
    public void execute() throws IOException {
        System.out.println("Enter ticker to be found:");

        final String input = UserInputUtil.stringValue();
        Derivative stock, futures, result;

        stock = STOCK_SERVICE.findByTicker(input);
        futures = FUTURES_SERVICE.findByTicker(input);

        if (stock != null) {
            result = stock;
        } else if (futures != null) {
            result = futures;
        } else {
            result = null;
        }

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("There is no such ticker in repositories.");
        }
    }
}
