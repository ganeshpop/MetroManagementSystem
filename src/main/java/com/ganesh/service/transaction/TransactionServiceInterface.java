package com.ganesh.service.transaction;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface TransactionServiceInterface {
    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    String swipeIn(int cardId, int sourceStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidStationException, InvalidSwipeInException;
    Transaction swipeOut(int cardId, int destinationStationId) throws SQLException, ClassNotFoundException, IOException, InvalidSwipeInException, InsufficientBalanceException, InvalidSwipeOutException, InvalidStationException;
}
