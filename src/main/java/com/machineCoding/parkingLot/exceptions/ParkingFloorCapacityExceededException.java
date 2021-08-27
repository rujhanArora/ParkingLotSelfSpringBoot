package com.machineCoding.parkingLot.exceptions;

public class ParkingFloorCapacityExceededException extends RuntimeException {
    public ParkingFloorCapacityExceededException(String message) {
        super("Parking Lot capacity Exceeded ! " + message);
    }
}
