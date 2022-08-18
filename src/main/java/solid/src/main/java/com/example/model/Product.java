package com.example.model;

import java.util.UUID;

public abstract class Product {
    protected long id;

    protected ProductType type;
    protected boolean available;
    protected String title;
    protected double price;

    public Product(long id, ProductType type, boolean available, String title, double price) {
        this.id = id;
        this.type = type;
        this.available = available;
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
