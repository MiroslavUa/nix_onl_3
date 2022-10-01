package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.annotation.Cash;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.annotation.handler.AutowiredAnnotationHandler;
import com.kulbachniy.homeworks.annotation.handler.SingletonAnnotationHandler;
import com.kulbachniy.homeworks.config.JdbcConfig;
import com.kulbachniy.homeworks.model.derivative.*;
import com.kulbachniy.homeworks.service.CurrencyPairService;
import com.kulbachniy.homeworks.service.FuturesService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) throws IOException {
//        CurrencyPair pair1 = new CurrencyPair("USDEUR", DerivativeType.CURRENCY_PAIR,
//                Exchange.FX, 121d, "USD", "EUR", LocalDateTime.now());
//        CurrencyPair pair2 = new CurrencyPair("EURCHF", DerivativeType.CURRENCY_PAIR,
//                Exchange.FX, 151d, "EUR", "CHF", LocalDateTime.now());
//        CurrencyPair pair3 = new CurrencyPair("GBPUSD", DerivativeType.CURRENCY_PAIR,
//                Exchange.FX, 161d, "GBP", "USD", LocalDateTime.now());
//        CurrencyPair pair4 = new CurrencyPair("EURJPY", DerivativeType.CURRENCY_PAIR,
//                Exchange.FX, 221d, "EUR", "JPY", LocalDateTime.now());
//        CurrencyPair pair5 = new CurrencyPair("GBPAUD", DerivativeType.CURRENCY_PAIR,
//                Exchange.FX, 521d, "GBPAUD", "AUD", LocalDateTime.now());
//
//        pair5.setTicker("I am billionaire");
//        pair5.setBaseCurrency("Done");
//        pair5.setQuoteCurrency("Done");
//        pair5.setPrice(10000000000d);
//        pair5.setId("851b1231-c950-41ee-86dd-0feb5f6e79ad");

        CurrencyPairService service = CurrencyPairService.getInstance();
        List<CurrencyPair> pairList = service.getAll();
        for (CurrencyPair p: pairList){
            System.out.println(p.getTicker());
        }
    }
}
