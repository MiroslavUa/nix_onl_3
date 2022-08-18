package com.example.model;

public class NotifiableProduct extends Product{
    protected String channel;

    public NotifiableProduct(long id, boolean available, String title, double price, String channel) {
        super(id, ProductType.NOTIFIABLE, available, title, price);
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
