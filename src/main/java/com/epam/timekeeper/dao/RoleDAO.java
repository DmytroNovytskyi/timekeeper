package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Role;

import java.util.List;

/**
 * Interface describes methods needed to be implemented for Role DAO to work as intended
 */
public interface RoleDAO {

    List<Role> readAll();

    Role readById(int id);

}
