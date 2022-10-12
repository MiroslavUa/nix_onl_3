package com.kulbachniy.homeworks.model.derivative;

import java.time.LocalDateTime;
import java.util.Objects;

public class CurrencyPair extends Derivative{
    private String baseCurrency;
    private String quoteCurrency;
    private LocalDateTime date;

    public CurrencyPair(String ticker, DerivativeType type,Exchange exchange, double price,
                        String baseCurrency, String quoteCurrency, LocalDateTime date) {
        super(ticker, type, exchange, price);
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.date = date;
    }

    public static class Builder{
        private String ticker;
        private DerivativeType type;
        private Exchange exchange;
        private double price;
        private String baseCurrency;
        private String quoteCurrency;
        private LocalDateTime date;

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

        public Builder setBaseCurrency(String baseCurrency){
            if(baseCurrency.length() > 20){
                throw new IllegalArgumentException("Base currency name must be less than 20 letters");
            }
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder setQuoteCurrency(String quoteCurrency){
            if(quoteCurrency.length() > 20){
                throw new IllegalArgumentException("Base currency name must be less than 20 letters");
            }
            this.quoteCurrency = quoteCurrency;
            return this;
        }

        public Builder setDate(LocalDateTime date){
            this.date = date;
            return this;
        }

        public CurrencyPair build(){
            if(ticker == null){
                throw new IllegalStateException("Ticker cannot be NULL");
            }

            if(type == null){
                throw new IllegalStateException("Type cannot be NULL");
            }

            if(exchange == null){
                throw new IllegalStateException("Exchange cannot be NULL");
            }

            return new CurrencyPair(ticker, type, exchange, price, baseCurrency, quoteCurrency, date);
        }
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPair)) return false;
        CurrencyPair that = (CurrencyPair) o;
        return baseCurrency.equals(that.baseCurrency) && quoteCurrency.equals(that.quoteCurrency) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, quoteCurrency, date);
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", price='" + super.getPrice() + '\'' +
                ", date=" + date +
                '}';
    }
}
