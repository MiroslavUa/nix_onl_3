package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.derivative.Derivative;

import java.util.Objects;

public class ContainerDerivative<T extends Derivative>{
    private final T derivative;
    private Long qnt = 1L;
    private final Double price;

    private double totalPrice;

    public ContainerDerivative(T derivative){
        this.derivative = derivative;
        this.price = Objects.requireNonNullElse(derivative.getPrice(), 0d);
        this.totalPrice = price * qnt;
    }

    public String getType() {
        return derivative.getClass().getSimpleName();
    }

    public Long getQuantity(){
        return qnt;
    }

    public <X extends Number> void changeQuantity(X x){
        Long multiplier = x.longValue();
        this.qnt = this.qnt * multiplier;
        this.totalPrice = this.qnt * this.price;
    }

    public void applyDiscount(){
        float min = 10.0f;
        float max = 30.0f;
        double discount = min + (1 + (max - min))*Math.random();
        System.out.println("Applied discount is: " + discount + " %");
        this.totalPrice = this.totalPrice - this.totalPrice * discount / 100;
    }

    public double getCurrentPrice(){
        return price;
    }

    public double getTotalPrice(){
        return totalPrice;
    }
}
