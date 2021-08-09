package com.ganesh.persistence.transaction;

import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface TransactionDaoInterface {

    Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean setDestinationStation(int stationId, int transactionId) throws SQLException, ClassNotFoundException, IOException;
    ArrayList<Transaction> getLastTransaction(int cardId) throws SQLException, ClassNotFoundException, IOException;
    int getTransactionDuration(int transactionId) throws SQLException, ClassNotFoundException, IOException;
    boolean createTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
    boolean completeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException, IOException;
}
