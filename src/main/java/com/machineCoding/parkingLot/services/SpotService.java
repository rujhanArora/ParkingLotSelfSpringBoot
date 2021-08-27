package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.exceptions.InvalidParkingSpotException;
import com.machineCoding.parkingLot.models.VehicleType;
import com.machineCoding.parkingLot.models.parkingSpot.*;
import com.machineCoding.parkingLot.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SpotService {
    List<ParkingSpot> parkingSpotList = new ArrayList<>();
    private Map<String, Integer> idToSpotsIndex = new HashMap<>();
    private Map<String, List<Integer>> floorToSpotsIndex = new HashMap<>();
    private Map<ParkingSpotType, List<Integer>> spotTypeToSpotsIndex = new HashMap<>();
    private static Map<VehicleType, List<ParkingSpotType>> VehicleTypeToParkingSpotType = new HashMap<>();
    static {
        VehicleTypeToParkingSpotType.put(VehicleType.BIKE, Arrays.asList(ParkingSpotType.BIKE));
        VehicleTypeToParkingSpotType.put(VehicleType.CAR, Arrays.asList(ParkingSpotType.CAR));
    }

    public ParkingSpot addSpot(ParkingSpotType spotType, String floorId) {
        ParkingSpot addedSpot;
        switch (spotType) {
            case BIKE:
                addedSpot = new BikeSpot(floorId);
                break;
            case CAR:
                addedSpot = new CarSpot(floorId);
                break;
            default:
                throw new InvalidParkingSpotException(spotType + " floorId: " + floorId);
        }
        parkingSpotList.add(addedSpot);
        int addedSpotIndex = parkingSpotList.size() - 1;
        floorToSpotsIndex.computeIfAbsent(floorId, spots -> new ArrayList<>())
                .add(addedSpotIndex);
        spotTypeToSpotsIndex.computeIfAbsent(spotType, spots -> new ArrayList<>())
                .add(addedSpotIndex);
        idToSpotsIndex.put(addedSpot.getId(), addedSpotIndex);
        printData();
        return addedSpot;
    }

    // Just for demonstration purpose
    private void printData() {
        log.info("parkingSpotList: {}", parkingSpotList);
        log.info("floorToSpotsIndex: {}", floorToSpotsIndex);
        log.info("spotTypeToSpotsIndex: {}", spotTypeToSpotsIndex);
    }

    public Optional<ParkingSpot> getFirstEmptySpot(VehicleType vehicleType) {
        List<ParkingSpotType> parkingSpotTypeList = VehicleTypeToParkingSpotType.get(vehicleType);
        for (ParkingSpotType parkingSpotType: parkingSpotTypeList) {
            List<Integer> spotIndices = spotTypeToSpotsIndex.get(parkingSpotType);
            for (Integer spotIndex : spotIndices) {
                ParkingSpot parkingSpot = parkingSpotList.get(spotIndex);
                if (parkingSpot.getSpotStatus().equals(SpotStatus.FREE)) {
                    return Optional.of(parkingSpot);
                }
            }
        }
        return Optional.empty();
    }

    public ParkingSpot getSpotById(String spotId) {
        return parkingSpotList.get(idToSpotsIndex.get(spotId));
    }

    // Synchronized to handle concurrency
    public void bookSpot(String spotId) {
        ParkingSpot parkingSpot = getSpotById(spotId);
        ValidationUtil.ensureTrue(parkingSpot.getSpotStatus().equals(SpotStatus.FREE), "SLot with id: " + spotId + " not free!");
        synchronized (SpotService.class) {
            ValidationUtil.ensureTrue(parkingSpot.getSpotStatus().equals(SpotStatus.FREE), "SLot with id: " + spotId + " not free!");
            parkingSpot.setSpotStatus(SpotStatus.BOOKED);
        }
    }

    public void vacateSpot(String spotId) {
        ParkingSpot parkingSpot = getSpotById(spotId);
        parkingSpot.setSpotStatus(SpotStatus.FREE);
    }

    public Optional<ParkingSpot> getSpot(VehicleType vehicleType, SpotFindStrategy spotFindStrategy) {
        switch (spotFindStrategy) {
            default:
                return getFirstEmptySpot(vehicleType);
        }
    }
}
