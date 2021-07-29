package com.ganesh.metro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface MetroDaoInterface {
    int addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
    boolean isAStation(int stationId) throws SQLException, ClassNotFoundException, IOException;
    boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean updateCardBalance(int cardId, int amount, boolean isCredit) throws SQLException, ClassNotFoundException, IOException;
    ArrayList<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
    boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
}
