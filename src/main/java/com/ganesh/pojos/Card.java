package com.ganesh.pojos;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor


public class Card {
    private int cardId;
    private final String cardType;
    private final int balance;
    private Timestamp activeSince;

    public Card(String cardType, int balance) {
        this.cardType = cardType;
        this.balance = balance;

    }

}
