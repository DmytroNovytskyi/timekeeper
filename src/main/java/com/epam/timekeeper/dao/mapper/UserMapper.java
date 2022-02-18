package com.epam.timekeeper.dao.mapper;

import com.epam.timekeeper.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements EntityMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        int i = 0;
        User user = new User();
        user.setId(resultSet.getInt(++i));
        user.setUsername(resultSet.getString(++i));
        user.setRoleId(resultSet.getInt(++i));
        user.setEmail(resultSet.getString(++i));
        user.setPassword(resultSet.getString(++i));
        user.setStatus(User.Status.valueOf(resultSet.getString(++i)));
        return user;
    }

}
