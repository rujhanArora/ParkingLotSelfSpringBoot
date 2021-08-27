package com.machineCoding.parkingLot.models;

import com.machineCoding.parkingLot.exceptions.ParkingFloorCapacityExceededException;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParkingLotFloor {
    private String id;
    private String name;
    private String parkingLotId;
    private final int capacity;
    @Getter
    private List<ParkingSpot> spotList = new ArrayList<>();

    public ParkingLotFloor(String name, String parkingLotId, int capacity) {
        this.id = TokenUtil.generateRandomTokenDefaultLength();
        this.name = name;
        this.parkingLotId = parkingLotId;
        this.capacity = capacity;
    }

    public void addSpot(ParkingSpot spot) {
        if (spotList.size() == capacity) {
            throw new ParkingFloorCapacityExceededException("Floor " + id + " has max capacity of " + capacity);
        }
        this.getSpotList().add(spot);
    }

    public boolean isSpotCapacityExceeded() {
        return spotList.size() >= capacity;
    }
}
