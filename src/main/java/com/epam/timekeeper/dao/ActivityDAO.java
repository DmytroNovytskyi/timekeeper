package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Activity;

import java.util.List;

/**
 * Interface describes methods needed to be implemented for Activity DAO to work as intended
 */
public interface ActivityDAO {

    void create(Activity entity);

    List<Activity> readAll();

    Activity readById(int id);

    void update(Activity entity);

}
