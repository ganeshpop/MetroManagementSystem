package com.ganesh.metro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class MetroService implements MetroServiceInterface {
    MetroServiceInterface metroService = new MetroService();

    @Override
    public int addCard(Card card) throws SQLException, ClassNotFoundException, IOException {
        return metroService.addCard(card);
    }

    @Override
    public Collection<Transaction> getAllTransactions(Card card) throws SQLException, ClassNotFoundException, IOException {
        return metroService.getAllTransactions(card);
    }

    @Override
    public Card getCardDetails(int cardId) throws SQLException, ClassNotFoundException, IOException {
        return metroService.getCardDetails(cardId);
    }

    @Override
    public Card updateCardBalance(int cardId, int amount) throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean swipeIn(Card card, Station station) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean swipeOut(Card card, Station station) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }
}
