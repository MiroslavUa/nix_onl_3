package com.kulbachniy.homeworks.model.containers;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.Comparator;

public class DerivativeComparator implements Comparator<Derivative> {
    public int compare(Derivative first, Derivative second){
        return Comparator.comparing(Derivative::getPrice).reversed()
                .thenComparing(Derivative::getTicker)
                .thenComparing(Derivative::getExchange)
                .compare(first, second);
    }
}
