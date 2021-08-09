package com.ganesh.persistence.transaction;

import com.ganesh.persistence.MySQLConnectionHelper;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionDao implements TransactionDaoInterface{

    @Override
    public Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, d.station_name AS destination_station_name, t.fare, t.fine, t.swipe_in_time_stamp, t.swipe_out_time_stamp, t.duration FROM transactions t LEFT JOIN stations s ON t.source_station_id = s.station_id LEFT JOIN stations d ON t.destination_station_id = d.station_id WHERE t.card_id = ?;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return TransactionDaoHelper.generateTransactions(resultSet);
    }
    @Override
    public boolean setDestinationStation(int stationId, int transactionId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        //PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET destination_station_id = ?, swipe_out_time_stamp = (SYSDATE() + INTERVAL 15 MINUTE) WHERE transaction_id = ?;");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET destination_station_id = ?, swipe_out_time_stamp = (SYSDATE() + INTERVAL 200 MINUTE) WHERE transaction_id = ?;");
        preparedStatement.setInt(1, stationId);
        preparedStatement.setInt(2, transactionId);
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public ArrayList<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, d.station_name AS destination_station_name, t.fare, t.fine, t.swipe_in_time_stamp, t.swipe_out_time_stamp, t.duration FROM transactions t LEFT JOIN stations s ON t.source_station_id = s.station_id LEFT JOIN stations d ON t.destination_station_id = d.station_id WHERE card_id = ? ORDER BY transaction_id DESC LIMIT 1;");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return TransactionDaoHelper.generateTransactions(resultSet);
    }
    @Override
    public int getTransactionDuration(int transactionId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT TIMESTAMPDIFF(MINUTE , swipe_in_time_stamp, swipe_out_time_stamp) as duration FROM transactions WHERE transaction_id = ?;");
        preparedStatement.setInt(1, transactionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int duration = resultSet.getInt("duration");
            connection.close();
            return duration;
        }
        return -1;
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
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET fare = ?, fine = ?, duration = ? WHERE transaction_id = ?;");
        preparedStatement.setInt(1, transaction.getFare());
        preparedStatement.setInt(2, transaction.getFine());
        preparedStatement.setInt(3, transaction.getDuration());
        preparedStatement.setInt(4, transaction.getTransactionId());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }
}
