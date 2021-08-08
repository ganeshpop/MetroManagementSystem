package com.ganesh.exceptions;

import com.ganesh.pojos.Color;

public class InvalidStationException extends Exception {
    public InvalidStationException() {
        super(Color.ANSI_RED + "You Have Selected an Invalid Station, Please Try Again");
    }

    public InvalidStationException(String message) {
        super(message);
    }
}
