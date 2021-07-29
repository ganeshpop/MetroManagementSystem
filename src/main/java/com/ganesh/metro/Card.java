package com.ganesh.metro;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor

public class Card {
    private int cardId;
    @NotNull
    private final String cardType;
    @NotNull
    private final int balance;
    @NotNull
    private final Timestamp activeSince;

}
