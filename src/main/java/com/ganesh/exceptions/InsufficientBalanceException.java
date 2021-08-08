package com.ganesh.exceptions;

import com.ganesh.pojos.Color;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super(Color.ANSI_RED + "Your Card has Insufficient Balance \nPlease Recharge Your Card\nHappy Travelling\ud83d\ude01 ");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
