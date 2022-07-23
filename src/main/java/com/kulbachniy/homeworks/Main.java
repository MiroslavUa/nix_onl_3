package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.model.containers.DerivativeComparator;
import com.kulbachniy.homeworks.model.containers.lists.AbstractDerivativeList;
import com.kulbachniy.homeworks.model.containers.lists.LinkedDerivativeList;
import com.kulbachniy.homeworks.model.derivative.*;
import com.kulbachniy.homeworks.model.containers.ContainerDerivative;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //ProgramRun.run();
        Stock a = new Stock("A", Exchange.NYSE, 1.0,
                "Company A", "Industry A", 100, 1, LocalDateTime.now());
        Stock b = new Stock("B", Exchange.NYSE, 2.0,
                "Company B", "Industry B", 200, 2, LocalDateTime.now());
        Stock c = new Stock("C", Exchange.NYSE, 3.0,
                "Company C", "Industry C", 300, 3, LocalDateTime.now());
        Stock d = new Stock("D", Exchange.NYSE, 4.0,
                "Company D", "Industry D", 400, 4, LocalDateTime.now());
        Stock e = new Stock("E", Exchange.NYSE, 5.0,
                "Company E", "Industry E", 500, 5, LocalDateTime.now());

        LinkedDerivativeList list = new LinkedDerivativeList();
        System.out.println(list.size());
        list.add(c); //enter 1 to test getByTransactionNumber(), replaceByTransactionNumber() methods
        list.add(d);
        list.add(e);
        list.addFirst(b);
        list.addFirst(a); //enter 5 to test getByTransactionNumber(), replaceByTransactionNumber() methods

        System.out.println("List contains following derivatives:");
        // 'foreach' in custom collection LinkedDerivativeList
        for (Derivative derivative : list) {
            System.out.println(derivative.getTicker());
        }

        //Total number of transactions in 'list'
        System.out.println("Total number of transaction is: " + list.size());

        //Date of first transaction
        System.out.println("\nDate of first transaction is: " + list.getDateOfFirstTransaction());

        //Date of last transaction
        System.out.println("Date of last transaction is: " + list.getDateOfLastTransaction());

        //Get derivative by transaction number
        System.out.println("\nTransaction 1 was performed with following derivative:\n " + list.getByTransactionNumber(1));
        System.out.println("Transaction 5 was performed with following derivative:\n " + list.getByTransactionNumber(5));

        //Delete derivative by transaction number
        list.removeByTransactionNumber(1);
        list.removeByTransactionNumber(5);

        System.out.println("\nAfter deleting 1st (C) and 5th (A) transactions list contains following derivatives:");
        //List of derivative after removeByTransactionNumber(1), removeByTransactionNumber(5)
        for (Derivative derivative : list) {
            System.out.println(derivative.getTicker());
        }

        //Replace derivative by transaction number
        list.replaceByTransactionNumber(4, a);
        list.replaceByTransactionNumber(2, b);
        list.replaceByTransactionNumber(3, c);

        System.out.println("\nAfter replacing list contains following derivatives:");
        //List of derivative after replaceByTransactionNumber()
        for (Derivative derivative : list) {
            System.out.println(derivative.getTicker());
        }

        Stock s1 = new Stock("C", Exchange.NYSE, 1.0,
                "Company C", "Industry C", 100, 1, LocalDateTime.now());
        Stock s2 = new Stock("B", Exchange.NASDAQ, 2.0,
                "Company B", "Industry B", 300, 1, LocalDateTime.now());
        Stock s3 = new Stock("A", Exchange.AMEX, 3.0,
                "Company A", "Industry A", 300, 1, LocalDateTime.now());

        Stock s4 = new Stock("C", Exchange.LSE, 4.0,
                "Company C", "Industry C", 400, 1, LocalDateTime.now());
        Stock s5 = new Stock("B", Exchange.LSE, 5.0,
                "Company B", "Industry B", 500, 1, LocalDateTime.now());
        Stock s6 = new Stock("A", Exchange.LSE, 6.0,
                "Company A", "Industry A", 600, 1, LocalDateTime.now());


        List<Derivative> arrayList = new ArrayList<>(){{add(s6); add(s1); add(s5); add(s2); add(s4); add(s3);}};
        System.out.println("Before sort");
        for(Derivative derivative : arrayList){
            System.out.println(derivative);
        }

        arrayList.sort(new DerivativeComparator());

        System.out.println("\nAfter sort");
        for(Derivative derivative : arrayList){
            System.out.println(derivative);
        }
    }
}
