package com.kulbachniy.homeworks.model.derivative;

import java.io.Serializable;
import java.util.UUID;

public abstract class Derivative{
    private String ticker;
    private DerivativeType type;
    private Exchange exchange;
    private Double price;
    private String id;

    protected Derivative(String ticker){
        this.ticker = ticker;
        this.id = UUID.randomUUID().toString();
    }

    protected Derivative(String ticker, DerivativeType type){
        this.ticker = ticker;
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }

    protected Derivative(String ticker, DerivativeType type, Exchange exchange, double price){
        this.id = UUID.randomUUID().toString();
        this.ticker = ticker;
        this.type = type;
        this.exchange = exchange;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public DerivativeType getType() {
        return type;
    }

    public void setType(DerivativeType type) {
        this.type = type;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {this.price = price;}

    public String getId() {
        return id;
    }

    public String setId(String id) {
        return this.id = id;
    }

}
