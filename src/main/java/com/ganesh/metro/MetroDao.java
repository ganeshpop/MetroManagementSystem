package com.ganesh.metro;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MetroDao implements MetroDaoInterface {

    @Override
    public int addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards(card_type, balance, active_since) VALUE(?,?,?)");
        preparedStatement.setString(1, card.getCardType());
        preparedStatement.setInt(2, card.getBalance());
        preparedStatement.setTimestamp(3, card.getActiveSince());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        if (affectedRows > 0) {
            preparedStatement = connection.prepareStatement("SELECT card_id FROM cards ORDER BY card_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.close();
            resultSet.next();
            return resultSet.getInt("card_id");
        }
        connection.close();
        return -1;
    }

    @Override
    public Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException {

        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, s1.station_name AS destination_station_name, t.fare, t.swipe_in_time_stamp, t.swipe_out_time_stamp FROM transactions t, stations s, stations s1 WHERE t.source_station_id = s.station_id AND t.destination_station_id = s1.station_id AND t.card_id = ?;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return generateTransactions(resultSet);
    }

    private Collection<Transaction> generateTransactions(ResultSet resultSet) throws SQLException {
        Collection<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            int transactionId = resultSet.getInt("transaction_id");
            int cardId = resultSet.getInt("card_id");
            Station source = new Station(resultSet.getInt("source_station_id"), resultSet.getString("source_station_name"));
            System.out.println(source);
            Station destination = new Station(resultSet.getInt("destination_station_id"), resultSet.getString("destination_station_name"));
            System.out.println(destination);
            int fare = resultSet.getInt("fare");
            Timestamp swipeInTimeStamp = resultSet.getTimestamp("swipe_in_time_stamp");
            Timestamp swipeOutTimeStamp = resultSet.getTimestamp("swipe_out_time_stamp");
            transactions.add(new Transaction(transactionId, cardId, source, destination, fare, swipeInTimeStamp, swipeOutTimeStamp));
        }
        return transactions;
    }


    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_type, balance, active_since FROM cards WHERE card_id = ?");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return new Card(cardId, resultSet.getString("card_type"), resultSet.getInt("balance"), resultSet.getTimestamp("active_since"));

    }

    @Override
    public boolean updateCardBalance(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cards SET balance = balance + ? WHERE card_id = ?;");
        preparedStatement.setInt(1, amount);
        preparedStatement.setInt(2, cardId);
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public Collection<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, d.station_name AS destination_station_name, t.fare, t.swipe_in_time_stamp, t.swipe_out_time_stamp FROM transactions t LEFT JOIN stations s ON t.source_station_id = s.station_id LEFT JOIN stations d ON t.destination_station_id = d.station_id WHERE card_id = ? ORDER BY transaction_id DESC LIMIT 1;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return generateTransactions(resultSet);
    }

    @Override
    public boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions(card_id, source_station_id, swipe_in_time_stamp) VALUE (?, ?, SYSDATE());");
        preparedStatement.setInt(1, transaction.getCardId());
        preparedStatement.setInt(2, transaction.getSourceStation().getStationId());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET destination_station_id = ?, fare = ?, swipe_out_time_stamp = SYSDATE() WHERE transaction_id = ?;");
        preparedStatement.setInt(1, transaction.getDestinationStation().getStationId());
        preparedStatement.setInt(2, transaction.getFare());
        preparedStatement.setInt(3, transaction.getTransactionId());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }


}
