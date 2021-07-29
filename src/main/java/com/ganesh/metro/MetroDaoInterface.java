package com.ganesh.metro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface MetroDaoInterface {
    int addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean updateCardBalance(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
    boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
}
