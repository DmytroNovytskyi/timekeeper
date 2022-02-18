package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.Category;

import java.sql.*;

public class CategoryPreparer implements Preparer<Category> {

    @Override
    public PreparedStatement prepareCreate(Connection connection, Category entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Category.CREATE,
                Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        statement.setString(++i, entity.getName());
        return statement;
    }

    @Override
    public PreparedStatement prepareReadAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL.Category.READ_ALL);
    }

    @Override
    public PreparedStatement prepareReadById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Category.READ_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

    @Override
    public PreparedStatement prepareUpdateById(Connection connection, Category entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Category.UPDATE_BY_ID);
        int i = 0;
        statement.setString(++i, entity.getName());
        statement.setString(++i, entity.getStatus().name());
        statement.setInt(++i, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Category.DELETE_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

}
