package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.User;

import java.util.List;

/**
 * Interface describes methods needed to be implemented for User DAO to work as intended
 */
public interface UserDAO {

    void create(User entity);

    List<User> readAll();

    User readById(int id);

    void update(User entity);

}
