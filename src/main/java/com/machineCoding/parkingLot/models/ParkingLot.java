package com.machineCoding.parkingLot.models;

import com.machineCoding.parkingLot.exceptions.ParkingLotCapacityExceededException;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParkingLot {
    private final int capacity;
    private int currentFilledCapacity = 0;
    private String id;
    private String name;
    private ParkingLotStatus status;
    @Getter
    private List<ParkingLotFloor> floors = new ArrayList<>();

    public ParkingLot(String name, int capacity) {
        this.id = TokenUtil.generateRandomTokenDefaultLength();
        this.name = name;
        this.setStatus(ParkingLotStatus.OPEN);
        this.capacity = capacity;
    }

    public void addFloor(ParkingLotFloor parkingLotFloor) {
        if (currentFilledCapacity + parkingLotFloor.getSpotList().size() > capacity) {
            throw new ParkingLotCapacityExceededException("capacity: " + capacity
            + " Remaining slots count: " + (capacity - currentFilledCapacity));
        }
        currentFilledCapacity += parkingLotFloor.getCapacity();
        this.getFloors().add(parkingLotFloor);
    }
}
