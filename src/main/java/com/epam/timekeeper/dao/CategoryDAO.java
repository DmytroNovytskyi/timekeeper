package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Category;

import java.util.List;

/**
 * Interface describes methods needed to be implemented for Category DAO to work as intended
 */
public interface CategoryDAO {

    void create(Category entity);

    List<Category> readAll();

    Category readById(int id);

    void update(Category entity);

}
