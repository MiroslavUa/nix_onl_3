package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.StockServiceParser;

import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        //ProgramRun.run();
        final StockServiceParser PARSER = StockServiceParser.getInstance();

        String jsonFile = "src/main/resources/stock.json";
        String xmlFile = "src/main/resources/stock.xml";

        Optional<Stock> lmt = PARSER.createStockFromJson(jsonFile);
        lmt.ifPresentOrElse(s -> System.out.println(s.getProduction().toString()), () -> System.out.println("Empty"));

        Optional<Stock> ba = PARSER.createStockFromJson(xmlFile);
        ba.ifPresentOrElse(s -> System.out.println(s.getProduction().toString()), () -> System.out.println("Empty"));
    }
}
