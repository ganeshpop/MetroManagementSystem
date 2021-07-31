package com.ganesh.interfaces;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;
import com.ganesh.pojos.Card;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface MetroServiceInterface {
    int getBalance(int cardId) throws SQLException, IOException, ClassNotFoundException;
    int addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
    boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
    boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
    String swipeIn(int cardId, int sourceStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidStationException, InvalidSwipeInException;
    Transaction swipeOut(int cardId, int destinationStationId) throws SQLException, ClassNotFoundException, IOException, InvalidSwipeInException, InsufficientBalanceException, InvalidSwipeOutException, InvalidStationException;
}
