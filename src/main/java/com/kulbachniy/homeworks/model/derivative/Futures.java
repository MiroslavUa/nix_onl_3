package com.kulbachniy.homeworks.model.derivative;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.time.LocalDateTime;
import java.util.Objects;

public class Futures extends Derivative{
    private String commodity;

    private LocalDateTime expirationDate;

    public Futures(String ticker, DerivativeType type, Exchange exchange, double price,
                   String commodity, LocalDateTime expirationDate) {
        super(ticker, type, exchange, price);
        this.commodity = commodity;
        this.expirationDate = expirationDate;
    }

    public static class Builder{
        private String ticker;
        private DerivativeType type;
        private Exchange exchange;
        private double price;
        private String commodity;
        private LocalDateTime expirationDate;

        public Builder setTicker(String ticker){
            if(ticker.equals("")){
                throw new IllegalArgumentException("Ticker must be non-empty string");
            }
            this.ticker = ticker;
            return this;
        }

        public Builder setDerivativeType(DerivativeType type){
            this.type = type;
            return this;
        }

        public Builder setExchange(Exchange exchange){
            this.exchange = exchange;
            return this;
        }

        public Builder setPrice(double price){
            if(price <= 0.d){
                throw new IllegalArgumentException("Price must be greater than 0");
            }
            this.price = price;
            return this;
        }

        public Builder setCommodity(String commodity){
            if(commodity.length() > 20){
                throw new IllegalArgumentException("Commodity name must be less than 20 letters");
            }
            this.commodity = commodity;
            return this;
        }

        public Builder setExpirationDate(LocalDateTime expirationDate){
            this.expirationDate = expirationDate;
            return this;
        }

        public Futures build(){
            if(ticker == null){
                throw new IllegalStateException("Ticker cannot be NULL");
            }

            if(type == null){
                throw new IllegalStateException("Type cannot be NULL");
            }

            if(exchange == null){
                throw new IllegalStateException("Exchange cannot be NULL");
            }

            return new Futures(ticker, type, exchange, price, commodity, expirationDate);
        }

    }
    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Futures)) return false;
        Futures futures = (Futures) o;
        return commodity.equals(futures.commodity) && expirationDate.equals(futures.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodity, expirationDate);
    }

    @Override
    public String toString() {
        return "Futures{ ticker: " + super.getTicker() + '\'' +
                ", commodity='" + commodity + '\'' +
                ", price='" + super.getPrice() + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
