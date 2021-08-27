package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.VehicleFilter;
import com.machineCoding.parkingLot.models.Vehicle;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VehicleService {
    private List<Vehicle> vehicles = new ArrayList<>();
    // More indices can be added based on search criteria
    private Map<String, Integer> vehicleNoToVehicleIndex = new HashMap<>();

    public Vehicle addVehicle(Vehicle vehicle) {
        Vehicle addedVehicle = Vehicle
                .builder()
                .id(TokenUtil.generateRandomTokenDefaultLength())
                .vehicleType(vehicle.getVehicleType())
                .customerId(vehicle.getCustomerId())
                .vehicleNo(vehicle.getVehicleNo())
                .build();
        vehicles.add(addedVehicle);
        vehicleNoToVehicleIndex.put(addedVehicle.getVehicleNo(), vehicles.size() - 1);
        log.info("added vehicle: {}", addedVehicle);
        return addedVehicle;
    }

    private List<Vehicle> filterByCustomerId(List<Vehicle> vehicles, String customerId) {
        return vehicles
                .stream()
                .filter(vehicle -> vehicle.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    private List<Vehicle> filterByVehicleNo(List<Vehicle> vehicles, String vehicleNo) {
        return vehicles
                .stream()
                .filter(vehicle -> vehicle.getVehicleNo().equals(vehicleNo)).collect(Collectors.toList());
    }

    public List<Vehicle> filterVehicles(VehicleFilter vehicleFilter) {
        List<Vehicle> filteredVehicles = this.vehicles;
        if (Objects.nonNull(vehicleFilter.getCustomerId())) {
            filteredVehicles = filterByCustomerId(filteredVehicles, vehicleFilter.getCustomerId());
        }
        if (Objects.nonNull(vehicleFilter.getVehicleNo())) {
            filteredVehicles = filterByVehicleNo(filteredVehicles, vehicleFilter.getVehicleNo());
        }
        return filteredVehicles;
    }
}
