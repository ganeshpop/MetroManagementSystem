package com.ganesh.metro;

import com.ganesh.helper.MetroDaoHelper;
import com.ganesh.helper.MySQLConnectionHelper;
import com.ganesh.interfaces.MetroDaoInterface;
import com.ganesh.pojos.Card;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MetroDao implements MetroDaoInterface {

    @Override
    public boolean addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards(card_type, balance, active_since) VALUE(?,?,SYSDATE())");
        preparedStatement.setString(1, card.getCardType());
        preparedStatement.setInt(2, card.getBalance());
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        connection.close();
        return (affectedRows > 0);
    }

    @Override
    public Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.transaction_id, t.card_id, t.source_station_id, s.station_name AS source_station_name, t.destination_station_id, d.station_name AS destination_station_name, t.fare, t.swipe_in_time_stamp, t.swipe_out_time_stamp FROM transactions t LEFT JOIN stations s ON t.source_station_id = s.station_id LEFT JOIN stations d ON t.destination_station_id = d.station_id WHERE t.card_id = ?;");
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
    public boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("UPDATE cards SET password =  ? WHERE card_id = ?;");
        preparedStatement.setString(1, password);
        preparedStatement.setInt(2, cardId);
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }

    @Override
    public boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_id FROM cards WHERE card_id = ? AND  password = ?;");
        preparedStatement.setInt(1,cardId);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }


    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_type, balance, active_since FROM cards WHERE card_id = ?");
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            return new Card(cardId, resultSet.getString("card_type"), resultSet.getInt("balance"), resultSet.getTimestamp("active_since"));
        else return null;
    }


    @Override
    public boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("UPDATE cards SET balance = balance + ? WHERE card_id = ?;");
        preparedStatement.setInt(1, amount);
        preparedStatement.setInt(2, cardId);
        int affectedRows = preparedStatement.executeUpdate();
        connection.commit();
        return affectedRows > 0;
    }


    @Override
    public boolean chargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("UPDATE cards SET balance = balance - ? WHERE card_id = ?;");
        preparedStatement.setInt(1, amount);
        preparedStatement.setInt(2, cardId);
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

    @Override
    public String getStation(int stationId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT station_name FROM stations WHERE station_id = ?");
        preparedStatement.setInt(1, stationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String stationName = resultSet.getString("station_name");
            connection.close();
            return stationName;
        }
        return null;

    }

    @Override
    public int getNewCardId() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_id FROM cards ORDER BY card_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int newCardId = resultSet.getInt("card_id");
            connection.close();
            return newCardId;
        }


    }



