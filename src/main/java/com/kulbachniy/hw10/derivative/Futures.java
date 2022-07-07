package com.kulbachniy.hw10.derivative;

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
