package com.ganesh.exceptions;

import com.ganesh.pojos.Color;

public class InvalidSwipeInException extends Exception {
    public InvalidSwipeInException() {
        super(Color.ANSI_RED + "You have not Swiped Out from Previous Trip - Invalid Swipe In Action");
    }

    public InvalidSwipeInException(String message) {
        super(message);
    }
}
