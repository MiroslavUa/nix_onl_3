package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Futures;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.DerivativeFactory;
import com.kulbachniy.homeworks.service.DerivativeService;
import com.kulbachniy.homeworks.service.FuturesService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Create implements Command{
    private static final DerivativeService<Stock> STOCK_SERVICE = StockService.getInstance();
    private static final DerivativeService<Futures> FUTURES_SERVICE = FuturesService.getInstance();

    @Override
    public void execute() throws IOException {
        System.out.println("Choose type of derivative to be added.");

        final DerivativeType[] values = DerivativeType.values();
        final List<String> names = Arrays.stream(DerivativeType.values())
                .map(Enum::name).toList();

        final int input = UserInputUtil.chooseCommand(names);
        switch  (values[input]){
            case FUTURES -> {
                System.out.println("Enter futures ticker: ");
                String ticker = UserInputUtil.stringValue().toUpperCase();
                System.out.println("Enter futures price: ");
                double price = UserInputUtil.doubleValue();
                Futures futures = new Futures.Builder()
                        .setTicker(ticker)
                        .setDerivativeType(DerivativeType.FUTURES)
                        .setExchange(Exchange.CME)
                        .setPrice(price)
                        .build();
                FUTURES_SERVICE.save(futures);
            }
            case STOCK -> {
                System.out.println("Enter stock ticker: ");
                String ticker = UserInputUtil.stringValue().toUpperCase();
                Stock stock = new Stock(ticker);
                STOCK_SERVICE.save(stock);
            }
        }
    }
}
