package com.epam.timekeeper.dao;

import com.epam.timekeeper.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> readAll();
    Role readById(int id);
}
