package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.command.ProgramRun;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.StockServiceParser;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        ProgramRun.run();

//        final StockServiceParser PARSER = StockServiceParser.getInstance();
//
//        String jsonFile = "stock.json";
//        String xmlFile = "stock.xml";
//
//        Optional<Stock> lmt = PARSER.createStockFromJson(jsonFile);
//        lmt.ifPresentOrElse(s -> System.out.println(s.getProduction().toString()), () -> System.out.println("Empty"));
//        lmt.ifPresentOrElse(s -> System.out.println(s.getDate().toString()), () -> System.out.println("Empty"));
//
//        Optional<Stock> ba = PARSER.createStockFromXml(xmlFile);
//        ba.ifPresentOrElse(s -> System.out.println(s.getProduction().toString()), () -> System.out.println("Empty"));
//        ba.ifPresentOrElse(s -> System.out.println(s.getDate().toString()), () -> System.out.println("Empty"));
    }
}
