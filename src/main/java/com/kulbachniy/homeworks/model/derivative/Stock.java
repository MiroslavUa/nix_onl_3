package com.kulbachniy.homeworks.model.derivative;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Stock extends Derivative {
    private String companyName;
    private String industry;
    private double volume;
    private double averageTrueRange;
    private LocalDateTime date;
    private List<String> production;

    public Stock(String ticker){
        super(ticker, DerivativeType.STOCK);
    };

    public Stock(String ticker, Exchange exchange, double price,
                 String companyName, String industry, double volume, double averageTrueRange, LocalDateTime date, List<String> production) {
        super(ticker, DerivativeType.STOCK, exchange, price);
        this.companyName = companyName;
        this.industry = industry;
        this.volume = volume;
        this.averageTrueRange = averageTrueRange;
        this.date = date;
        this.production = production;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAverageTrueRange() {
        return averageTrueRange;
    }

    public void setAverageTrueRange(double averageTrueRange) {
        this.averageTrueRange = averageTrueRange;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<String> getProduction() {
        return production;
    }

    public void setProduction(List<String> production) {
        this.production = production;
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
                ", exchange='" + super.getExchange() + '\'' +
                "\t, companyName='" + companyName + '\'' +
                "\t, industry='" + industry + '\'' +
                "\t, price=" + super.getPrice() + '\'' +
                "\t, volume=" + volume +
                "\t, averageTrueRange=" + averageTrueRange +
                "\tid: '" + super.getId() + '\'' +
                "\t, date=" + date +
                '}';
    }
}
