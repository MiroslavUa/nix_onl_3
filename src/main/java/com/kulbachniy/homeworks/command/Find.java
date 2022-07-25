package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.DerivativeService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.IOException;

public class Find implements Command{
    private static final DerivativeService<Stock> STOCK_DERIVATIVE_SERVICE = StockService.getInstance();
    @Override
    public void execute() throws IOException {
        System.out.print("Enter ticker to search: ");
        String ticker = UserInputUtil.stringValue().toUpperCase();
        Stock stock = (Stock) STOCK_DERIVATIVE_SERVICE.findByTicker(ticker);
        if (stock != null) {
            System.out.println(stock);
        } else {
            System.out.println("There is not " + ticker + " in base");
        }
    }
}
