package com.epam.timekeeper.dao.preparer;

import com.epam.timekeeper.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Preparer<T extends Entity> {

    PreparedStatement prepareCreate(Connection connection, T entity) throws SQLException;

    PreparedStatement prepareReadAll(Connection connection) throws SQLException;

    PreparedStatement prepareReadById(Connection connection, int id) throws SQLException;

    PreparedStatement prepareUpdateById(Connection connection, T entity) throws SQLException;

    PreparedStatement prepareDeleteById(Connection connection, int id) throws SQLException;

}
