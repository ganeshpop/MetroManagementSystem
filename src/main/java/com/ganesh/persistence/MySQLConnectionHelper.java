package com.ganesh.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnectionHelper {
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("DbConnection.properties");
        properties.load(fileInputStream);

        Class.forName(properties.getProperty("DB.DRIVER_CLASS_NAME"));
        Connection connection = DriverManager.getConnection(properties.getProperty("DB.URL"), properties.getProperty("DB.USER_NAME"), properties.getProperty("DB.PASSWORD"));
        connection.setAutoCommit(false);
        return connection;
    }

}
