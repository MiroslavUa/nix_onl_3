package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.model.containers.DerivativeComparator;
import com.kulbachniy.homeworks.model.containers.lists.AbstractDerivativeList;
import com.kulbachniy.homeworks.model.containers.lists.LinkedDerivativeList;
import com.kulbachniy.homeworks.model.containers.trees.BinaryTree;
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
        Stock f = new Stock("F", Exchange.NYSE, 6.0,
                "Company F", "Industry F", 600, 5, LocalDateTime.now());
        Stock x = new Stock("X", Exchange.NYSE, 0.1,
                "Company X", "Industry X", 150, 5, LocalDateTime.now());

        BinaryTree tree = new BinaryTree();
        tree.addDerivative(d);
        tree.addDerivative(a);
        tree.addDerivative(b);
        tree.addDerivative(c);
        tree.addDerivative(e);
        tree.addDerivative(f);
        tree.addDerivative(x);

        System.out.println("Tree has " + tree.getSize() + " elements.");

        System.out.println("\nTree structure: ");
        tree.printTreeStructure();

        System.out.println("\nInfo on each elements: ");
        tree.printTreeInfo();

        System.out.println("\nTotal price of left branch elements: ");
        System.out.println(tree.getSumLeftBranch());

        System.out.println("Total price of right branch elements: ");
        System.out.println(tree.getSumRightBranch());
    }
}
