package com.machineCoding.parkingLot;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleFilter {
    private String vehicleNo;
    private String customerId;
    private String vehicleId;
}
