package com.epam.timekeeper.dao;

import com.epam.timekeeper.dao.mapper.EntityMapper;
import com.epam.timekeeper.dao.preparer.Preparer;
import com.epam.timekeeper.entity.Entity;
import com.epam.timekeeper.exception.ActivityOpenException;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO<T extends Entity> {

    private final Preparer<T> preparer;
    private final EntityMapper<T> mapper;

    public DAO(Preparer<T> preparer, EntityMapper<T> mapper) {
        this.preparer = preparer;
        this.mapper = mapper;
    }

    public void create(T entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = preparer.prepareCreate(connection, entity);
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            rollback(connection);
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

    public List<T> readAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = preparer.prepareReadAll(connection);
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

    public T readById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = preparer.prepareReadById(connection, id);
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

    public void update(T entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = preparer.prepareUpdateById(connection, entity);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            if (e.getSQLState().equals("23000")) {
                throw new AlreadyExistsException(e.getMessage());
            } else if (e.getSQLState().equals("45000")) {
                throw new ActivityOpenException(e.getMessage());
            } else {
                throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
            }
        } finally {
            close(statement);
            close(connection);
        }
    }

    public void deleteById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = preparer.prepareDeleteById(connection, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
    }

    private List<T> mapList(Statement statement) throws SQLException {
        List<T> entities = null;
        try (ResultSet resultSet = statement.getResultSet()) {
            if (resultSet.next()) {
                entities = new ArrayList<>();
                entities.add(mapper.map(resultSet));
                while (resultSet.next()) {
                    entities.add(mapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return entities;
    }

    private T mapEntity(Statement statement) throws SQLException {
        T entity = null;
        try (ResultSet resultSet = statement.getResultSet()) {
            if (resultSet.next()) {
                entity = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return entity;
    }

    private void close(AutoCloseable connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
