package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.derivative.Derivative;

import java.util.List;

public interface CrudRepository {
    void save(Derivative derivative);

    void saveAll(List<Derivative> derivatives);

    boolean update(Derivative derivative);

    boolean delete(String id);

    Derivative findById(String id);

    Derivative findByTicker(String ticker);

    List<Derivative> getAll();
}
