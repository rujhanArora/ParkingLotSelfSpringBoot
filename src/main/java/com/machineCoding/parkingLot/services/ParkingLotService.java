package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.models.ParkingLot;
import com.machineCoding.parkingLot.models.ParkingLotFloor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ParkingLotService {
    List<ParkingLot> parkingLots = new ArrayList<>();
    Map<String, Integer> idToParkingLotInd = new HashMap<>();

    public ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot(name);
        parkingLots.add(parkingLot);
        idToParkingLotInd.put(parkingLot.getId(), parkingLots.size() - 1);
        log.info("added parkingLot: {}", parkingLot);
        return parkingLot;
    }

    public ParkingLot getParkingLotById(String parkingLotId) {
        return parkingLots.get(idToParkingLotInd.get(parkingLotId));
    }
    // Add use RBAC
    public ParkingLotFloor addFloor(String parkingLotId, String floorName) {
        ParkingLot parkingLot = getParkingLotById(parkingLotId);
        ParkingLotFloor parkingLotFloor = new ParkingLotFloor(floorName, parkingLotId);
        parkingLot.addFloor(parkingLotFloor);
        log.info("added floor: {}", parkingLotFloor);
        return parkingLotFloor;
    }
}
