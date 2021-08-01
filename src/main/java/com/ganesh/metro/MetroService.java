package com.ganesh.metro;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;
import com.ganesh.helper.MetroServiceHelper;
import com.ganesh.interfaces.MetroDaoInterface;
import com.ganesh.interfaces.MetroServiceInterface;
import com.ganesh.pojos.Card;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class MetroService implements MetroServiceInterface {
    MetroDaoInterface metroDao = new MetroDao();

    @Override
    public int getBalance(int cardId) throws SQLException, IOException, ClassNotFoundException {
        if(metroDao.getCardDetails(cardId) == null) return -1;
        return metroDao.getCardDetails(cardId).getBalance();
    }

    @Override
    public int addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        if(metroDao.addCard(card)){
            return metroDao.getNewCardId();
        }
        return -1;
    }

    @Override
    public Collection<Transaction> getAllTransactions(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return metroDao.getAllTransactions(cardId);
    }

////
    public int getFine(int transactionId) throws SQLException, ClassNotFoundException, IOException {
        int duration =  metroDao.getTransactionDuration(transactionId);
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

    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return metroDao.getCardDetails(cardId);
    }

    @Override
    public boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return metroDao.isACard(cardId);
    }

    @Override
    public boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        return metroDao.setPassword(cardId,password);
    }

    @Override
    public boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        return metroDao.validatePassword(cardId, password);
    }

    @Override
    public boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
            if(amount > 0) return metroDao.rechargeCard(cardId, amount);
            else return false;
    }

    @Override
    public Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException {
        return metroDao.getAllStations();
    }

    @Override
    public String swipeIn(int cardId, int sourceStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidStationException, InvalidSwipeInException {
        /* 1. Accept the user’s input as source station.
           2. The station can be from the above list only. Create a custom exception (with a meaningful message to the user) to handle invalid station inputs.
           3. Validate the minimum required balance in the card. The user should have minimum balance of Rs 20 in the card. If the balance is not there, throw custom exception with appropriate message to user and do not allow to swipe in.
           4. On successful swipe in, which means if the minimum balance is present then print the message as “You have successfully swiped in at the station” + <Source Station Name>.
        */
        if(metroDao.isAStation(sourceStationId)) {
            if(metroDao.getCardDetails(cardId).getBalance() >= 20) {
                Transaction lastTransaction =  metroDao.getLastTransaction(cardId).get(0);
                if (lastTransaction.getTransactionId() == 0 || lastTransaction.getDestinationStation() != null) {
                    metroDao.createTransaction(new Transaction(cardId, new Station(sourceStationId)));
                    return metroDao.getStation(sourceStationId);
                }
                else throw new InvalidSwipeInException();
            } else throw new InsufficientBalanceException();
        } else throw new InvalidStationException();

    }

    @Override
    public Transaction swipeOut(int cardId, int destinationStationId) throws SQLException, ClassNotFoundException, IOException, InsufficientBalanceException, InvalidSwipeOutException, InvalidStationException {
        /*
        1. Accept the user’s input for destination station.
        2. The station can be from the above list only.
        Create a custom exception (with a meaningful message to the user) to handle invalid station inputs.
        3. Calculate the total fare based on source and destination stations and deduct the fare from the card balance. The fare calculation is based on the below rules.
        a. Fare between any 2 adjacent stations is Rs 5. Example – Fare between L1 and L2 is Rs 5 and fare between L2 and L3 is Rs 5.
        4. After deducting balance, the message needs to be printed “You have successfully swiped out with card balance as” + <actual card balance>
        */
        if(metroDao.isAStation(destinationStationId)){
                Transaction lastTransaction =  metroDao.getLastTransaction(cardId).get(0);
                if (lastTransaction.getSourceStation() != null && lastTransaction.getDestinationStation() == null) {
                    metroDao.setDestinationStation(destinationStationId, lastTransaction.getTransactionId());
                    int fare = MetroServiceHelper.calculateFare(lastTransaction.getSourceStation(), new Station(destinationStationId));
                    int fine = getFine(lastTransaction.getTransactionId());
                    fare += fine;
                    int duration = metroDao.getTransactionDuration(lastTransaction.getTransactionId());
                    if (metroDao.completeTransaction(new Transaction(lastTransaction.getTransactionId(),fare,fine,duration)))
                        metroDao.chargeCard(cardId, fare);
                } else throw new InvalidSwipeOutException();
        } else throw new InvalidStationException();
        return metroDao.getLastTransaction(cardId).get(0);
    }
}

