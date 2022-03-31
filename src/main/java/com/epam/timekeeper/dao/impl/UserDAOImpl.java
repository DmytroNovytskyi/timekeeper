package com.epam.timekeeper.dao.impl;

import com.epam.timekeeper.dao.MySQLConnector;
import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.dao.UserDAO;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends UtilDAO implements UserDAO {

    @Override
    public void create(User entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.User.CREATE);
            statement.setString(++i, entity.getUsername());
            statement.setInt(++i, entity.getRoleId());
            statement.setString(++i, entity.getEmail());
            statement.setString(++i, entity.getPassword());
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
    public List<User> readAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.User.READ_ALL);
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
    public User readById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.User.READ_BY_ID);
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
    public void update(User entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(SQL.User.UPDATE_BY_ID);
            statement.setString(++i, entity.getUsername());
            statement.setString(++i, entity.getEmail());
            statement.setString(++i, entity.getPassword());
            statement.setString(++i, entity.getStatus().name());
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


    private List<User> mapList(Statement statement) throws SQLException {
        List<User> entities = null;
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

    private User mapEntity(Statement statement) throws SQLException {
        User entity = null;
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

    private User map(ResultSet resultSet) throws SQLException {
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
