package com.kulbachniy.hw10.repository;

import com.kulbachniy.hw10.derivative.Derivative;
import com.kulbachniy.hw10.derivative.DerivativeType;

import java.util.List;

public interface CrudRepository {
    void create(Derivative derivative);
    boolean update(Derivative derivative);
    boolean delete(String id);
    Derivative findById(String id);
    Derivative findByTicker(String ticker);

    List<Derivative> getAll();
}
