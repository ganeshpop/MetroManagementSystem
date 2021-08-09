package com.ganesh.service.card;

import com.ganesh.persistence.card.CardDao;
import com.ganesh.persistence.card.CardDaoInterface;
import com.ganesh.pojos.Card;

import java.io.IOException;
import java.sql.SQLException;

public class CardService implements  CardServiceInterface{
    CardDaoInterface cardDao = new CardDao();
    @Override
    public int getBalance(int cardId) throws SQLException, IOException, ClassNotFoundException {
        if(cardDao.getCardDetails(cardId) == null) return -1;
        return cardDao.getCardDetails(cardId).getBalance();
    }
    @Override
    public int addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        if(cardDao.addCard(card)){
            return cardDao.getNewCardId();
        }
        return -1;
    }
    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return cardDao.getCardDetails(cardId);
    }

    @Override
    public boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return cardDao.isACard(cardId);
    }

    @Override
    public boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        return cardDao.setPassword(cardId,password);
    }

    @Override
    public boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException {
        return cardDao.validatePassword(cardId, password);
    }

    @Override
    public boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
        if(amount > 0) return cardDao.rechargeCard(cardId, amount);
        else return false;
    }
}
