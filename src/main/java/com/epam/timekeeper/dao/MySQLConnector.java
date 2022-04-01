package com.epam.timekeeper.dao;

import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.FileInteractionException;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * When first loaded to memory initializes static BasicDataSource object
 * with parameters read from resource file with name "application.properties".
 * For connector to work properly properties with names as "driverClassName",
 * "url", "user", "password", "maxActive", "maxIdle", "maxWait" should be set into the file;
 */
public class MySQLConnector {
    private static final String PROPERTIES = "application.properties";
    private static final String DRIVER = "driverClassName";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String MAX_ACTIVE = "maxActive";
    private static final String MAX_IDLE = "maxIdle";
    private static final String MAX_WAIT = "maxWait";

    private static final BasicDataSource dataSource;

    private MySQLConnector(){}

    static {
        try {
            Properties properties = new Properties();
            properties.load(MySQLConnector.class.getResourceAsStream("/" + PROPERTIES));

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty(DRIVER));
            dataSource.setUrl(properties.getProperty(URL));
            dataSource.setUsername(properties.getProperty(USER));
            dataSource.setPassword(properties.getProperty(PASSWORD));
            dataSource.setMaxActive(Integer.parseInt(properties.getProperty(MAX_ACTIVE)));
            dataSource.setMaxIdle(Integer.parseInt(properties.getProperty(MAX_IDLE)));
            dataSource.setMaxWait(Integer.parseInt(properties.getProperty(MAX_WAIT)));

        } catch (FileNotFoundException e) {
            throw new FileInteractionException(PROPERTIES + " file not found");
        } catch (IOException e) {
            throw new FileInteractionException(PROPERTIES + " load error");
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException("database connection error");
        }
    }

}
