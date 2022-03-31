package com.epam.timekeeper.dao.impl;

import com.epam.timekeeper.exception.ActivityOpenException;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;

import java.sql.Connection;
import java.sql.SQLException;

public class UtilDAO {

    protected void processSQLExceptionOnDBChange(SQLException e){
        e.printStackTrace();
        if (e.getSQLState().equals("23000")) {
            throw new AlreadyExistsException(e.getMessage());
        } else if (e.getSQLState().equals("45000")) {
            throw new ActivityOpenException(e.getMessage());
        } else {
            throw new DBException("State: " + e.getSQLState() + " Message: " + e.getMessage());
        }
    }

    protected void close(AutoCloseable connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    protected void rollbackTransaction(Connection connection) {
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
