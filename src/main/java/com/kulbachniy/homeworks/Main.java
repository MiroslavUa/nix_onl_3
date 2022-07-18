package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.derivative.CurrencyPair;
import com.kulbachniy.homeworks.derivative.Exchange;
import com.kulbachniy.homeworks.derivative.Futures;
import com.kulbachniy.homeworks.derivative.Stock;
import com.kulbachniy.homeworks.repository.ContainerDerivative;

import java.sql.SQLOutput;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        //ProgramRun.run();

        ContainerDerivative container;

        Stock stock =  new Stock("BA", Exchange.NYSE, 100, "Boeing", "Aerospace Defense",
                12345612.5, 12.5, LocalDateTime.now());
        container = new ContainerDerivative(stock);
        System.out.println("Type of derivative: " + container.getType());
        System.out.println("Initial quantity: " + container.getQuantity());
        System.out.println("Current price: " + container.getCurrentPrice());
        System.out.println("Total price: " + container.getTotalPrice());

        Integer i = Integer.valueOf("2");
        container.changeQuantity(i);
        System.out.println("Changed quantity: " + + container.getQuantity());
        System.out.println("Total price: " + container.getTotalPrice());

        container.applyDiscount();
        System.out.println("Total price after discount: " + container.getTotalPrice());


        Futures futures = new Futures("GOLD", Exchange.CME, 500, "Gold futures", LocalDateTime.now());
        container = new ContainerDerivative(futures);
        System.out.println("\nType of derivative: " + container.getType());
        System.out.println("Initial quantity: " + container.getQuantity());
        System.out.println("Current price: " + container.getCurrentPrice());
        System.out.println("Total price: " + container.getTotalPrice());

        Float f = Float.valueOf(2.059f);
        container.changeQuantity(f);
        System.out.println("Changed quantity: " + + container.getQuantity());
        System.out.println("Total price: " + container.getTotalPrice());

        container.applyDiscount();
        System.out.println("Total price after discount: " + container.getTotalPrice());


        CurrencyPair pair = new CurrencyPair("USDEUR",Exchange.FX, 150,
                "United States Dollar", "Euro", LocalDateTime.now());
        container = new ContainerDerivative(pair);
        System.out.println("\nType of derivative: " + container.getType());
        System.out.println("Initial quantity: " + container.getQuantity());
        System.out.println("Current price: " + container.getCurrentPrice());
        System.out.println("Total price: " + container.getTotalPrice());

        Double d = Double.valueOf("2d");
        container.changeQuantity(d);
        System.out.println("Changed quantity: " + + container.getQuantity());
        System.out.println("Total price: " + container.getTotalPrice());

        container.applyDiscount();
        System.out.println("Total price after discount: " + container.getTotalPrice());


    }
}
