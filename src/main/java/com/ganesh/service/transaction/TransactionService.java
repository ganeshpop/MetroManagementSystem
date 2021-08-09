package com.ganesh.service.transaction;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;
import com.ganesh.persistence.card.CardDao;
import com.ganesh.persistence.card.CardDaoInterface;
import com.ganesh.persistence.station.StationDao;
import com.ganesh.persistence.station.StationDaoInterface;
import com.ganesh.persistence.transaction.TransactionDao;
import com.ganesh.persistence.transaction.TransactionDaoInterface;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class TransactionService implements TransactionServiceInterface {
    TransactionDaoInterface transactionDao = new TransactionDao();
    CardDaoInterface cardDao = new CardDao();
    StationDaoInterface stationDao = new StationDao();
    @Override
    public Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return transactionDao.getAllTransactions(cardId);
    }
    @Override
    public String swipeIn(int cardId, int sourceStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidStationException, InvalidSwipeInException {
        if(stationDao.isAStation(sourceStationId)) {
            if(cardDao.getCardDetails(cardId).getBalance() >= 20) {
                Transaction lastTransaction =  transactionDao.getLastTransaction(cardId).get(0);
                if (lastTransaction.getTransactionId() == 0 || lastTransaction.getDestinationStation() != null) {
                    transactionDao.createTransaction(new Transaction(cardId, new Station(sourceStationId)));
                    return stationDao.getStation(sourceStationId);
                }
                else throw new InvalidSwipeInException();
            } else throw new InsufficientBalanceException();
        } else {
            throw new InvalidStationException();
        }

    }

    @Override
    public Transaction swipeOut(int cardId, int destinationStationId) throws SQLException, ClassNotFoundException, IOException, InvalidSwipeOutException, InvalidStationException {
        if(stationDao.isAStation(destinationStationId)){
            Transaction lastTransaction =  transactionDao.getLastTransaction(cardId).get(0);
            if (lastTransaction.getSourceStation() != null && lastTransaction.getDestinationStation() == null) {
                transactionDao.setDestinationStation(destinationStationId, lastTransaction.getTransactionId());
                int fare = TransactionServiceHelper.calculateFare(lastTransaction.getSourceStation(), new Station(destinationStationId));
                int fine = getFine(lastTransaction.getTransactionId());
                fare += fine;
                int duration = transactionDao.getTransactionDuration(lastTransaction.getTransactionId());
                if (transactionDao.completeTransaction(new Transaction(lastTransaction.getTransactionId(),fare,fine,duration)))
                    cardDao.chargeCard(cardId, fare);
            } else throw new InvalidSwipeOutException();
        } else throw new InvalidStationException();
        return transactionDao.getLastTransaction(cardId).get(0);
    }
    public int getFine(int transactionId) throws SQLException, ClassNotFoundException, IOException {
        int duration =  transactionDao.getTransactionDuration(transactionId);
        int extraHours;
        if (duration >= 0) {
            if(duration == 0) return 0;
            if(duration - 90 > 0 ) {
                int extraMinutes = duration - 90;
                if(extraMinutes % 60 == 0) {
                    extraHours = extraMinutes / 60;
                } else {
                    extraHours = 1 + extraMinutes / 60;
                }
                return extraHours * 100;
            } else return 0;
        }else return -1;
    }
}
