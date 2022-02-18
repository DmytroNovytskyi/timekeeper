package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.User;

import java.sql.*;

public class UserPreparer implements Preparer<User> {

    @Override
    public PreparedStatement prepareCreate(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.User.CREATE, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        statement.setString(++i, entity.getUsername());
        statement.setInt(++i, entity.getRoleId());
        statement.setString(++i, entity.getEmail());
        statement.setString(++i, entity.getPassword());
        return statement;
    }

    @Override
    public PreparedStatement prepareReadAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL.User.READ_ALL);
    }

    @Override
    public PreparedStatement prepareReadById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.User.READ_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

    @Override
    public PreparedStatement prepareUpdateById(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.User.UPDATE_BY_ID);
        int i = 0;
        statement.setString(++i, entity.getUsername());
        statement.setString(++i, entity.getEmail());
        statement.setString(++i, entity.getPassword());
        statement.setString(++i, entity.getStatus().name());
        statement.setInt(++i, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.User.DELETE_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

}
