package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.Activity;

import java.sql.*;

public class ActivityPreparer implements Preparer<Activity> {

    @Override
    public PreparedStatement prepareCreate(Connection connection, Activity entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Activity.CREATE,
                Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        statement.setInt(++i, entity.getCategoryID());
        statement.setString(++i, entity.getName());
        return statement;
    }

    @Override
    public PreparedStatement prepareReadAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL.Activity.READ_ALL);
    }

    @Override
    public PreparedStatement prepareReadById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Activity.READ_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

    @Override
    public PreparedStatement prepareUpdateById(Connection connection, Activity entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Activity.UPDATE_BY_ID);
        int i = 0;
        statement.setInt(++i, entity.getCategoryID());
        statement.setString(++i, entity.getName());
        statement.setString(++i, entity.getStatus().name());
        statement.setInt(++i, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.Activity.DELETE_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

}
