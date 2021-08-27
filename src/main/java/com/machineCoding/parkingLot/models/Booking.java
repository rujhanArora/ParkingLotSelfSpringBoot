package com.machineCoding.parkingLot.models;

import com.machineCoding.parkingLot.models.Vehicle;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Booking {
    private String id;
    private String customerId;
    // Vehicle id because customer can bring different vehicles at different times
    private String vehicleId;
    private Date issuedAt;
    private String parkingSpotId;
    private BookingStatus status;
    private Double amountPaid;

    public Booking(String customerId, String vehicleId, String parkingSpotId) {
        this.setId(TokenUtil.generateRandomTokenDefaultLength());
        this.setCustomerId(customerId);
        this.setVehicleId(vehicleId);
        this.setParkingSpotId(parkingSpotId);
        this.setIssuedAt(new Date());
        this.setStatus(BookingStatus.ACTIVE);
    }
}
