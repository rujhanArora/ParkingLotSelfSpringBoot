package com.machineCoding.parkingLot.models.parkingSpot;

import com.machineCoding.parkingLot.utils.TokenUtil;

public class CarSpot extends ParkingSpot {
    public CarSpot(String floorId) {
        super(TokenUtil.generateRandomTokenDefaultLength(), floorId, SpotStatus.FREE, ParkingSpotType.CAR);
    }
}