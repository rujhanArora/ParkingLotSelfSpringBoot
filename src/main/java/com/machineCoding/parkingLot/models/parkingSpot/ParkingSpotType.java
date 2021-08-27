package com.machineCoding.parkingLot.models.parkingSpot;

public enum ParkingSpotType {
    BIKE(10.0), CAR(20.0);
    private Double baseRate;
    ParkingSpotType(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getBaseRate() {
        return baseRate;
    }
}
