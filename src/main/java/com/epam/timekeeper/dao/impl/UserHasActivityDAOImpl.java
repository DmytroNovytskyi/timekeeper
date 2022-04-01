package com.epam.timekeeper.dao.impl;

import com.epam.timekeeper.dao.MySQLConnector;
import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.dao.UserHasActivityDAO;
import com.epam.timekeeper.entity.UserHasActivity;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for UserHasActivityDAO interface.
 */
public class UserHasActivityDAOImpl extends UtilDAO implements UserHasActivityDAO {

    @Override
    public void create(UserHasActivity entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.UserHasActivity.CREATE);
            statement.setInt(++i, entity.getUserId());
            statement.setInt(++i, entity.getActivityId());
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            rollbackTransaction(connection);
            e.printStackTrace();
            if (e.getSQLState().equals("23000")) {
                throw new AlreadyExistsException(e.getMessage());
            } else {
                throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
            }
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<UserHasActivity> readAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.UserHasActivity.READ_ALL);
            statement.execute();
            return mapList(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public UserHasActivity readById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.UserHasActivity.READ_BY_ID);
            statement.setInt(++i, id);
            statement.execute();
            return mapEntity(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void update(UserHasActivity entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.UserHasActivity.UPDATE_BY_ID);
            statement.setString(++i, entity.getStatus().name());
            statement.setTimestamp(++i, entity.getStartTime());
            statement.setTimestamp(++i, entity.getEndTime());
            statement.setInt(++i, entity.getId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction(connection);
            processSQLExceptionOnDBChange(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private List<UserHasActivity> mapList(Statement statement) throws SQLException {
        List<UserHasActivity> entities = null;
        try (ResultSet resultSet = statement.getResultSet()) {
            if (resultSet.next()) {
                entities = new ArrayList<>();
                entities.add(map(resultSet));
                while (resultSet.next()) {
                    entities.add(map(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return entities;
    }

    private UserHasActivity mapEntity(Statement statement) throws SQLException {
        UserHasActivity entity = null;
        try (ResultSet resultSet = statement.getResultSet()) {
            if (resultSet.next()) {
                entity = map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return entity;
    }

    private UserHasActivity map(ResultSet resultSet) throws SQLException {
        int i = 0;
        UserHasActivity userHasActivity = new UserHasActivity();
        userHasActivity.setId(resultSet.getInt(++i));
        userHasActivity.setUserId(resultSet.getInt(++i));
        userHasActivity.setActivityId(resultSet.getInt(++i));
        userHasActivity.setStatus(UserHasActivity.Status.valueOf(resultSet.getString(++i)));
        userHasActivity.setStartTime(resultSet.getTimestamp(++i));
        userHasActivity.setEndTime(resultSet.getTimestamp(++i));
        userHasActivity.setCreationDate(resultSet.getTimestamp(++i));
        return userHasActivity;
    }

}
