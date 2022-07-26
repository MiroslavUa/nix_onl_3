package com.kulbachniy.homeworks.model.derivative;

import java.time.LocalDateTime;
import java.util.Objects;

public class Futures extends Derivative{
    private String commodity;

    private LocalDateTime expirationDate;

    public Futures(String ticker){
        super(ticker, DerivativeType.FUTURES);
    };
    public Futures(String ticker, Exchange exchange, double price,
                   String commodity, LocalDateTime expirationDate) {
        super(ticker, DerivativeType.FUTURES, exchange, price);
        this.commodity = commodity;
        this.expirationDate = expirationDate;
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
        return "Futures{" +
                "commodity='" + commodity + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
