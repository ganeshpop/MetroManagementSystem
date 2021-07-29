package com.ganesh.exceptions;

public class InvalidSwipeInException extends Exception {
    public InvalidSwipeInException() {
        System.out.println("You have not Swiped Out from Previous Trip - Invalid Swipe In Action");
    }

    public InvalidSwipeInException(String message) {
        super(message);
    }
}
