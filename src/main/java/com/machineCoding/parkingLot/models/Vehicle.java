package com.machineCoding.parkingLot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Vehicle {
    private String id;
    private String customerId;
    private String vehicleNo;
    private VehicleType vehicleType;
}
