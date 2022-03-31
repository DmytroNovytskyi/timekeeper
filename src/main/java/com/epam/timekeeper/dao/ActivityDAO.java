package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Activity;

import java.util.List;

public interface ActivityDAO {
    void create(Activity entity);
    List<Activity> readAll();
    Activity readById(int id);
    void update(Activity entity);
}
