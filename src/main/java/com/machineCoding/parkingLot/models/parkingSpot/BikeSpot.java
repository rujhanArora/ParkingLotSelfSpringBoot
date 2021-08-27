package com.machineCoding.parkingLot.models.parkingSpot;

import com.machineCoding.parkingLot.utils.TokenUtil;

public class BikeSpot extends ParkingSpot {
    public BikeSpot(String floorId) {
        super(TokenUtil.generateRandomTokenDefaultLength(), floorId, SpotStatus.FREE, ParkingSpotType.BIKE);
    }
}
