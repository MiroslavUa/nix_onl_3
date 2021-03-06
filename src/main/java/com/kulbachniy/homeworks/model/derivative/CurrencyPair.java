package com.kulbachniy.homeworks.model.derivative;

import java.time.LocalDateTime;
import java.util.Objects;

public class CurrencyPair extends Derivative{
    private String baseCurrency;
    private String quoteCurrency;
    private LocalDateTime date;

    public CurrencyPair(String ticker){
        super(ticker);
    };

    public CurrencyPair(String ticker, Exchange exchange, double price,
                        String baseCurrency, String quoteCurrency, LocalDateTime date) {
        super(ticker, DerivativeType.CURRENCY_PAIR, exchange, price);
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
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
