package com.kulbachniy.hw10.derivative;

import java.util.UUID;

public abstract class Derivative {
    private String ticker;
    private DerivativeType type;
    private Exchange exchange;
    private Double price;
    private String id;

    protected Derivative(){}
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        return this.id = id;
    }

}
