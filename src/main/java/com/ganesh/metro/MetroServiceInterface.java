package com.ganesh.metro;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface MetroServiceInterface {
    int addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean updateCardBalance(int cardId, int amount, boolean  isCredit) throws SQLException, ClassNotFoundException, IOException;
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
    boolean swipeIn(int cardId, int sourceStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidStationException, InvalidSwipeInException;
    Transaction swipeOut(int cardId, int destinationStationId) throws SQLException, ClassNotFoundException, IOException, InvalidSwipeInException, InsufficientBalanceException, InvalidSwipeOutException, InvalidStationException;
}
