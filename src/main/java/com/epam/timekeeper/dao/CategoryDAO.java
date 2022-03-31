package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Category;

import java.util.List;

public interface CategoryDAO {
    void create(Category entity);
    List<Category> readAll();
    Category readById(int id);
    void update(Category entity);
}
