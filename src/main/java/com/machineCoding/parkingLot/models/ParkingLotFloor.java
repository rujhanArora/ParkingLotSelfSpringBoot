package com.machineCoding.parkingLot.models;

import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParkingLotFloor {
    private String id;
    private String name;
    private String parkingLotId;
    private List<ParkingSpot> spotList = new ArrayList<>();

    public ParkingLotFloor(String name, String parkingLotId) {
        this.id = TokenUtil.generateRandomTokenDefaultLength();
        this.name = name;
        this.parkingLotId = parkingLotId;
    }
}
