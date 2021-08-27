package com.machineCoding.parkingLot.models.parkingSpot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ParkingSpot {
    private String id;
    private String floorId;
    private SpotStatus spotStatus;
    private ParkingSpotType parkingSpotType;
}
