package com.example.model;

public class ProductBundle extends Product{
    protected int amount;

    public ProductBundle(long id, boolean available, String title, double price, int amount) {
        super(id, ProductType.BUNDLE, available, title, price);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                "amount=" + amount +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
