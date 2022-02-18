package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.Role;

import java.sql.*;

public class RolePreparer implements Preparer<Role> {

    @Override
    public PreparedStatement prepareCreate(Connection connection, Role entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Role.CREATE,
                Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        statement.setString(++i, entity.getName());
        return statement;
    }

    @Override
    public PreparedStatement prepareReadAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL.Role.READ_ALL);
    }

    @Override
    public PreparedStatement prepareReadById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Role.READ_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

    @Override
    public PreparedStatement prepareUpdateById(Connection connection, Role entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Role.UPDATE_BY_ID);
        int i = 0;
        statement.setString(++i, entity.getName());
        statement.setInt(++i, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Role.DELETE_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

}
