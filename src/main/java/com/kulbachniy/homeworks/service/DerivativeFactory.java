package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.model.derivative.*;
import com.kulbachniy.homeworks.model.derivative.*;

public class DerivativeFactory {
    private DerivativeFactory(){}

    public static Derivative createDerivative(DerivativeType type, String ticker){
        return switch(type){
            case STOCK -> new Stock(ticker);
            case FUTURES -> new Futures(ticker);
            case CURRENCY_PAIR -> new CurrencyPair(ticker);
            default -> throw new IllegalArgumentException("Unknown derivative type: " + type);
        };
    }
}
