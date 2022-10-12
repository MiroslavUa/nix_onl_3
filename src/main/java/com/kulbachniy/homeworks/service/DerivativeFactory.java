package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.*;
import com.kulbachniy.homeworks.model.derivative.*;

public class  DerivativeFactory {
    private DerivativeFactory(){}

    public static Derivative createDerivative(DerivativeType type, String ticker, double price){
        return switch(type){
            case STOCK -> new Stock(ticker);
            case FUTURES ->  new Futures.Builder()
                    .setTicker(ticker)
                    .setDerivativeType(type)
                    .setExchange(Exchange.CME)
                    .setPrice(price)
                    .build();
            case CURRENCY_PAIR -> new CurrencyPair.Builder()
                    .setTicker(ticker)
                    .setDerivativeType(type)
                    .setExchange(Exchange.FX)
                    .setPrice(price)
                    .build();
            default -> throw new IllegalArgumentException("Unknown derivative type: " + type);
        };
    }
}
