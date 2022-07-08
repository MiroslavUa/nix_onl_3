package com.kulbachniy.hw10.derivative;

import java.time.LocalDateTime;
import java.util.Objects;

public class Stock extends Derivative {
    private String companyName;
    private String industry;
    private double volume;
    private double averageTrueRange;
    private LocalDateTime date;

    public Stock(String ticker, Exchange exchange, double price,
                 String companyName, String industry, double volume, double averageTrueRange, LocalDateTime date) {
        super(ticker, DerivativeType.STOCK, exchange, price);
        this.companyName = companyName;
        this.industry = industry;
        this.volume = volume;
        this.averageTrueRange = averageTrueRange;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return volume == stock.volume && averageTrueRange == stock.averageTrueRange && companyName.equals(stock.companyName) && industry.equals(stock.industry) && date.equals(stock.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, industry, volume, averageTrueRange, date);
    }

    @Override
    public String toString() {
        return "Stock{ ticker: " + super.getTicker() + '\'' +
                "id: " + super.getId() + '\'' +
                "companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", volume=" + volume +
                ", averageTrueRange=" + averageTrueRange +
                ", date=" + date +
                '}';
    }
}
