package com.ganesh.persistence.card;

import com.ganesh.persistence.MySQLConnectionHelper;
import com.ganesh.pojos.Card;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDao implements CardDaoInterface {

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
    public int getNewCardId() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT card_id FROM cards ORDER BY card_id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int newCardId = resultSet.getInt("card_id");
        connection.close();
        return newCardId;
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


}
