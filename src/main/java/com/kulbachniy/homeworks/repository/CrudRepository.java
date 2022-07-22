package com.kulbachniy.homeworks.repository;

import com.kulbachniy.homeworks.derivative.Derivative;

import java.util.ArrayList;
import java.util.List;

public interface CrudRepository<T extends Derivative> {
    void save(T derivative);

    void saveAll(List<T> derivatives);

    boolean update(T derivative);

    boolean delete(String id);

    T findById(String id);

    T findByTicker(String ticker);

    List<T> getAll();
}
