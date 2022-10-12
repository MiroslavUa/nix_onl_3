package com.kulbachniy.homeworks.model;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Portfolio {
    private String id;
    private double sum;
    private final List<Derivative> derivativeList;
    private LocalDateTime time;

    public Portfolio() {
        this.id = UUID.randomUUID().toString();
        this.derivativeList = new ArrayList<>();
        this.time = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public List<Derivative> getDerivativeList() {
        return derivativeList;
    }

    public void addDerivatives(List<? extends Derivative> derivatives){
        derivatives.forEach(this::addDerivative);
    }

    public void addDerivative(Derivative derivative){
        derivativeList.add(derivative);
        sum += derivative.getPrice();
        time = LocalDateTime.now();
    }

    public boolean deleteDerivative(Derivative derivative){
        if(derivativeList.removeIf(d -> Objects.equals(d.getId(), derivative.getId()))){
            sum -= derivative.getPrice();
            time = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", sum=" + sum +
                ", derivativeList=" + derivativeList.size() +
                ", time=" + time +
                '}';
    }
}
