package com.ganesh.metro;


import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
@ToString
@Getter
public class Transaction {
    private final int cardId;
    private  int transactionId;
    private  Station sourceStation;
    private  Station destinationStation;
    private  int fare;
    private Timestamp swipeInTimeStamp;
    private Timestamp swipeOutTimeStamp;

    public Transaction(int cardId, int transactionId, Station sourceStation, Station destinationStation, int fare, Timestamp swipeInTimeStamp, Timestamp swipeOutTimeStamp) {
        this.cardId = cardId;
        this.transactionId = transactionId;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.fare = fare;
        this.swipeInTimeStamp = swipeInTimeStamp;
        this.swipeOutTimeStamp = swipeOutTimeStamp;
    }

    public Transaction(int cardId, Station sourceStation){
        this.cardId = cardId;
        this.sourceStation = sourceStation;
    }
}
