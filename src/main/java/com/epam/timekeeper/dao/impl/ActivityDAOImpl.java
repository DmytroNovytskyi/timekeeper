package com.epam.timekeeper.dao.impl;

import com.epam.timekeeper.dao.ActivityDAO;
import com.epam.timekeeper.dao.MySQLConnector;
import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAOImpl extends UtilDAO implements ActivityDAO {
    @Override
    public void create(Activity entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.Activity.CREATE);
            statement.setInt(++i, entity.getCategoryID());
            statement.setString(++i, entity.getName());
            statement.setString(++i , entity.getDescription());
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
    public List<Activity> readAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.Activity.READ_ALL);
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
    public Activity readById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.Activity.READ_BY_ID);
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
    public void update(Activity entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.Activity.UPDATE_BY_ID);
            statement.setInt(++i, entity.getCategoryID());
            statement.setString(++i, entity.getName());
            statement.setString(++i, entity.getStatus().name());
            statement.setString(++i, entity.getDescription());
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

    private List<Activity> mapList(PreparedStatement statement) throws SQLException {
        List<Activity> entities = null;
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

    private Activity mapEntity(Statement statement) throws SQLException {
        Activity entity = null;
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

    private Activity map(ResultSet resultSet) throws SQLException {
        int i = 0;
        Activity activity = new Activity();
        activity.setId(resultSet.getInt(++i));
        activity.setCategoryID(resultSet.getInt(++i));
        activity.setName(resultSet.getString(++i));
        activity.setStatus(Activity.Status.valueOf(resultSet.getString(++i)));
        activity.setDescription(resultSet.getString(++i));
        return activity;
    }

}
