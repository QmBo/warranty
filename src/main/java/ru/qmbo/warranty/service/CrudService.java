package ru.qmbo.warranty.service;

import java.util.List;

public interface CrudService<T> {

    T findById(Integer id);

    List<T> findAll();

}
