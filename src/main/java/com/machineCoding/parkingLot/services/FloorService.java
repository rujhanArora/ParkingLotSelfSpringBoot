package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.exceptions.ParkingFloorCapacityExceededException;
import com.machineCoding.parkingLot.models.ParkingLotFloor;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpotType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FloorService {
    @Autowired SpotService spotService;

    List<ParkingLotFloor> parkingLotFloorList = new ArrayList<>();
    Map<String, Integer> floorIdToIndex = new HashMap<>();

    ParkingLotFloor addFloor(String parkingLotId, String floorName, int capacity) {
        ParkingLotFloor parkingLotFloor = new ParkingLotFloor(floorName, parkingLotId, capacity);
        parkingLotFloorList.add(parkingLotFloor);
        floorIdToIndex.put(parkingLotFloor.getId(), parkingLotFloorList.size() - 1);
        log.info("Added parkingLotFloor: " + parkingLotFloor);
        return parkingLotFloor;
    }

    private ParkingLotFloor getFloorById(String floorId) {
        return parkingLotFloorList.get(floorIdToIndex.get(floorId));
    }

    public ParkingSpot addSpot(ParkingSpotType spotType, String floorId) {
        ParkingLotFloor parkingLotFloor = getFloorById(floorId);
        if (parkingLotFloor.isSpotCapacityExceeded()) {
            throw new ParkingFloorCapacityExceededException("Floor " + floorId + " has no capacity remaining ");
        }
        ParkingSpot parkingSpot = spotService.addSpot(spotType, floorId);
        parkingLotFloor.addSpot(parkingSpot);
        return parkingSpot;
    }
}
