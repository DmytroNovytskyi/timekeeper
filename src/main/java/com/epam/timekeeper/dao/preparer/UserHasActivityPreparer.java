package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.UserHasActivity;

import java.sql.*;

public class UserHasActivityPreparer implements Preparer<UserHasActivity> {

    @Override
    public PreparedStatement prepareCreate(Connection connection, UserHasActivity entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.UserHasActivity.CREATE,
                Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        statement.setInt(++i, entity.getUserId());
        statement.setInt(++i, entity.getActivityId());
        return statement;
    }

    @Override
    public PreparedStatement prepareReadAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL.UserHasActivity.READ_ALL);
    }

    @Override
    public PreparedStatement prepareReadById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.UserHasActivity.READ_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

    @Override
    public PreparedStatement prepareUpdateById(Connection connection, UserHasActivity entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.UserHasActivity.UPDATE_BY_ID);
        int i = 0;
        statement.setString(++i, entity.getStatus().name());
        statement.setTimestamp(++i, entity.getStartTime());
        statement.setTime(++i, entity.getTimeSpent());
        statement.setInt(++i, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL.UserHasActivity.DELETE_BY_ID);
        int i = 0;
        statement.setInt(++i, id);
        return statement;
    }

}
