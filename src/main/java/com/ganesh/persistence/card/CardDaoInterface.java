package com.ganesh.persistence.card;

import com.ganesh.pojos.Card;

import java.io.IOException;
import java.sql.SQLException;

public interface CardDaoInterface {

    boolean addCard(Card card) throws SQLException, ClassNotFoundException, IOException;
    int getNewCardId() throws SQLException, ClassNotFoundException, IOException;
    boolean rechargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    boolean chargeCard(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException;
    Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean isACard(int cardId) throws SQLException, ClassNotFoundException, IOException;
    boolean setPassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
    boolean validatePassword(int cardId, String password) throws SQLException, ClassNotFoundException, IOException;
}
