package com.epam.timekeeper.dao.mapper;

import com.epam.timekeeper.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements EntityMapper<Role> {

    @Override
    public Role map(ResultSet resultSet) throws SQLException {
        int i = 0;
        Role role = new Role();
        role.setId(resultSet.getInt(++i));
        role.setName(resultSet.getString(++i));
        return role;
    }

}
