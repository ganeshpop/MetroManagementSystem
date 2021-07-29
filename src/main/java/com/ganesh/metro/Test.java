package com.ganesh.metro;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        try {

            Connection connection = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_id FROM cards ORDER BY card_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt("card_id"));

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
