package com.ganesh.interfaces;

import com.ganesh.pojos.Card;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface MetroDaoInterface {
    boolean addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
    boolean isAStation(int stationId) throws SQLException, ClassNotFoundException, IOException;
    boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
    boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean chargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    ArrayList<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
    boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
    String getStation(int stationId) throws SQLException, ClassNotFoundException, IOException;
    int getNewCardId() throws SQLException, ClassNotFoundException, IOException;
}
