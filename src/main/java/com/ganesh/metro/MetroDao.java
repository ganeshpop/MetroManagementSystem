package com.ganesh.metro;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MetroDao implements MetroDaoInterface {

    @Override
    public int addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
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

        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, s1.station_name AS destination_station_name, t.fare, t.swipe_in_time_stamp, t.swipe_out_time_stamp FROM transactions t, stations s, stations s1 WHERE t.source_station_id = s.station_id AND t.destination_station_id = s1.station_id AND t.card_id = ?;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return MetroDaoHelper.generateTransactions(resultSet);
    }

    @Override
    public Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stations;");
        ResultSet resultSet = preparedStatement.executeQuery();
        return MetroDaoHelper.generateStations(resultSet);
    }

    @Override
    public boolean isAStation(int stationId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT station_id FROM stations WHERE station_id = ?;");
        preparedStatement.setInt(1,stationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_id FROM cards WHERE card_id = ?;");
        preparedStatement.setInt(1,cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();

    }


    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_type, balance, active_since FROM cards WHERE card_id = ?");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return new Card(cardId, resultSet.getString("card_type"), resultSet.getInt("balance"), resultSet.getTimestamp("active_since"));

    }


    @Override
    public boolean updateCardBalance(int cardId, int amount, boolean isCredit) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cards SET balance = balance ? ? WHERE card_id = ?;");
        preparedStatement.setString(1, (isCredit)? "+":"-");
        preparedStatement.setInt(2, amount);
        preparedStatement.setInt(3, cardId);
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public ArrayList<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, d.station_name AS destination_station_name, t.fare, t.swipe_in_time_stamp, t.swipe_out_time_stamp FROM transactions t LEFT JOIN stations s ON t.source_station_id = s.station_id LEFT JOIN stations d ON t.destination_station_id = d.station_id WHERE card_id = ? ORDER BY transaction_id DESC LIMIT 1;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return MetroDaoHelper.generateTransactions(resultSet);
    }

    @Override
    public boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions(card_id, source_station_id, swipe_in_time_stamp) VALUE (?, ?, SYSDATE());");
        preparedStatement.setInt(1, transaction.getCardId());
        preparedStatement.setInt(2, transaction.getSourceStation().getStationId());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET destination_station_id = ?, fare = ?, swipe_out_time_stamp = SYSDATE() WHERE transaction_id = ?;");
        preparedStatement.setInt(1, transaction.getDestinationStation().getStationId());
        preparedStatement.setInt(2, transaction.getFare());
        preparedStatement.setInt(3, transaction.getTransactionId());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }


}
