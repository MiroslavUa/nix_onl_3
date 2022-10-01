package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.model.Portfolio;
import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.List;
import java.util.Map;

public interface PortfolioRepository {
    void save(Portfolio derivative);

    void saveAll(List<Portfolio> derivatives);

    boolean update(Portfolio derivative);

    boolean delete(String id);

    Portfolio findById(String id);

    List<Portfolio> getAll();

    List<Portfolio> getExpensiveThan(double sum);

    int quantity();

    void updateTime(Derivative derivative);

    Map<Double, List<Portfolio>> groupBySum();
}
