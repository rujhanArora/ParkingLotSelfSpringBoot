package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.exceptions.ParkingLotCapacityExceededException;
import com.machineCoding.parkingLot.models.ParkingLot;
import com.machineCoding.parkingLot.models.ParkingLotFloor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ParkingLotService {
    @Autowired FloorService floorService;

    List<ParkingLot> parkingLots = new ArrayList<>();
    Map<String, Integer> idToParkingLotInd = new HashMap<>();

    public ParkingLot createParkingLot(String name, int capacity) {
        ParkingLot parkingLot = new ParkingLot(name, capacity);
        parkingLots.add(parkingLot);
        idToParkingLotInd.put(parkingLot.getId(), parkingLots.size() - 1);
        log.info("added parkingLot: {}", parkingLot);
        return parkingLot;
    }

    public ParkingLot getParkingLotById(String parkingLotId) {
        return parkingLots.get(idToParkingLotInd.get(parkingLotId));
    }
    // Add use RBAC
    public ParkingLotFloor addFloor(String parkingLotId, String floorName, int floorCapacity) {
        ParkingLot parkingLot = getParkingLotById(parkingLotId);
        if (parkingLot.getCurrentFilledCapacity() + floorCapacity > parkingLot.getCapacity()) {
            throw new ParkingLotCapacityExceededException("Parking Lot floorCapacity: " + parkingLot.getCapacity()
                    + " Remaining slots count: " + (parkingLot.getCapacity() - parkingLot.getCurrentFilledCapacity()));
        }
        ParkingLotFloor addedFloor = floorService.addFloor(parkingLotId, floorName, floorCapacity);
        parkingLot.setCurrentFilledCapacity(parkingLot.getCurrentFilledCapacity() + floorCapacity);
        return addedFloor;
    }
}
