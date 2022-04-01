package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.UserHasActivity;

import java.util.List;

/**
 * Interface describes methods needed to be implemented for UserHasActivity DAO to work as intended
 */
public interface UserHasActivityDAO {

    void create(UserHasActivity entity);

    List<UserHasActivity> readAll();

    UserHasActivity readById(int id);

    void update(UserHasActivity entity);

}
