package com.ganesh.exceptions;

import com.ganesh.pojos.Color;

public class InvalidSwipeOutException extends Exception {

    public InvalidSwipeOutException() {
        super(Color.ANSI_RED + "You Have not Swiped In at any station - Invalid Swipe Out Action");
    }

    public InvalidSwipeOutException(String message) {
        super(message);
    }


}
