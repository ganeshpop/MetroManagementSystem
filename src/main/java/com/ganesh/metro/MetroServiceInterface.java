package com.ganesh.metro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface MetroServiceInterface {
    int addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(Card card) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Card updateCardBalance(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    boolean swipeIn(Card card, Station station) throws SQLException, ClassNotFoundException, IOException;
    boolean swipeOut(Card card, Station station) throws SQLException, ClassNotFoundException, IOException;
}
