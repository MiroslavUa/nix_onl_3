package com.kulbachniy.homeworks.model;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.time.LocalDateTime;
import java.util.List;

public class Trade {
    private String id;
    private double sum;
    private List<Derivative> derivativeList;
    private LocalDateTime time;
}
