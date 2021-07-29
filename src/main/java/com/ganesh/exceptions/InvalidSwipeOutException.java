package com.ganesh.exceptions;

public class InvalidSwipeOutException extends Exception {

    public InvalidSwipeOutException() {
        System.out.println("You Have not Swiped In at any station - Invalid Swipe Out Action");
    }

    public InvalidSwipeOutException(String message) {
        super(message);
    }


}
