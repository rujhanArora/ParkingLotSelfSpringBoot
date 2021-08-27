package com.machineCoding.parkingLot.exceptions;

public class InvalidParkingSpotException extends RuntimeException {
    public InvalidParkingSpotException(String message) {
        super("Invalid Spot: " + message);
    }
}
