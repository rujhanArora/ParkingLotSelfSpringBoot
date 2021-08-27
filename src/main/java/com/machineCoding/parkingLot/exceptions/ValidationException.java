package com.machineCoding.parkingLot.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Validation Error: " + message);
    }
}