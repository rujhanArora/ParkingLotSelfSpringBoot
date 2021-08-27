package com.machineCoding.parkingLot.models;

import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParkingLot {
    private String id;
    private String name;
    private ParkingLotStatus status;
    @Getter
    private List<ParkingLotFloor> floors = new ArrayList<>();

    public ParkingLot(String name) {
        this.id = TokenUtil.generateRandomTokenDefaultLength();
        this.name = name;
        this.setStatus(ParkingLotStatus.OPEN);
    }

    public void addFloor(ParkingLotFloor parkingLotFloor) {
        this.getFloors().add(parkingLotFloor);
    }
}
