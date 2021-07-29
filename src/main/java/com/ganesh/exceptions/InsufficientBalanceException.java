package com.ganesh.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Your Card has Insufficient Balance Please Recharge Your Card Happy Travelling!\ud83d\ude01! ");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}