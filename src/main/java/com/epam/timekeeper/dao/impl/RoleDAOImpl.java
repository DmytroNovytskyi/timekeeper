package com.epam.timekeeper.dao.impl;

import com.epam.timekeeper.dao.MySQLConnector;
import com.epam.timekeeper.dao.RoleDAO;
import com.epam.timekeeper.dao.SQL;
import com.epam.timekeeper.entity.Role;
import com.epam.timekeeper.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RoleDAO interface.
 */
public class RoleDAOImpl extends UtilDAO implements RoleDAO {


    @Override
    public List<Role> readAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.Role.READ_ALL);
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
    public Role readById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = 0;
        try {
            connection = MySQLConnector.getConnection();
            statement = connection.prepareStatement(SQL.Role.READ_BY_ID);
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

    private List<Role> mapList(Statement statement) throws SQLException {
        List<Role> entities = null;
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

    private Role mapEntity(Statement statement) throws SQLException {
        Role entity = null;
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

    private Role map(ResultSet resultSet) throws SQLException {
        int i = 0;
        Role role = new Role();
        role.setId(resultSet.getInt(++i));
        role.setName(resultSet.getString(++i));
        return role;
    }

}
