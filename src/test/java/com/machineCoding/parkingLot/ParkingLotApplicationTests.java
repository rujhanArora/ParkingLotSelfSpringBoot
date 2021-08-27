package com.machineCoding.parkingLot;

import com.machineCoding.parkingLot.DTOs.CustomerDTO;
import com.machineCoding.parkingLot.models.*;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpotType;
import com.machineCoding.parkingLot.models.parkingSpot.SpotStatus;
import com.machineCoding.parkingLot.services.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
class ParkingLotApplicationTests {

	@Autowired
	CustomerService customerService;
	@Autowired
	ParkingLotService parkingLotService;
	@Autowired
	SpotService spotService;
	@Autowired
	BookingService bookingService;
	@Autowired
	VehicleService vehicleService;

	@Test
	void contextLoads() {
		ParkingLot parkingLot = parkingLotService.createParkingLot("ABC corp");
		Assertions.assertThat(parkingLot.getName().equals("ABC corp"));

		ParkingLotFloor parkingLotFloor = parkingLotService.addFloor(parkingLot.getId(), "Ground Floor");
		Assertions.assertThat(parkingLot.getFloors().size() == 1);

		ParkingSpot carSpot = spotService.addSpot(ParkingSpotType.CAR, parkingLotFloor.getId());
		ParkingSpot bikeSpot = spotService.addSpot(ParkingSpotType.BIKE, parkingLotFloor.getId());

		Assertions.assertThat(carSpot.getId().equals(spotService.getSpotById(carSpot.getId())));
		Optional<ParkingSpot> emptyCarSpot = spotService.getFirstEmptySpot(VehicleType.CAR);
		Assertions.assertThat(carSpot.getId().equals(emptyCarSpot.get().getId()));

		Customer customer = customerService.addCustomer(CustomerDTO.builder().email("rj").name("Rj Arora").build());
		Vehicle carVehicle = vehicleService.addVehicle(Vehicle.builder().vehicleNo("HR15G5772").vehicleType(VehicleType.CAR).customerId(customer.getId()).build());
		Assertions.assertThat(carVehicle.getId().equals(vehicleService.filterVehicles(VehicleFilter.builder().vehicleNo(carVehicle.getVehicleNo()).build()).get(0).getId()));

		Booking carBooking = bookingService.checkIn(customer.getId(), carVehicle.getId(), carSpot.getId());
		Assertions.assertThat(spotService.getSpotById(carSpot.getId()).getSpotStatus().equals(SpotStatus.BOOKED));

		Optional<ParkingSpot> secondEmptyCarSpot = spotService.getFirstEmptySpot(VehicleType.CAR);
		Assertions.assertThat(!secondEmptyCarSpot.isPresent());

		try {
			bookingService.checkIn(customer.getId(), carVehicle.getId(), carSpot.getId());
		} catch (Exception e) {
			log.info("e: " + e.getMessage());
		}

		try {
			bookingService.checkOut(carBooking.getId());
		} catch (Exception e) {
			log.info("e: " + e.getMessage());
		}
		bookingService.payForBooking(carBooking.getId());
		bookingService.checkOut(carBooking.getId());
		Optional<ParkingSpot> emptiedSpot = spotService.getFirstEmptySpot(VehicleType.CAR);
		Assertions.assertThat(emptiedSpot.isPresent());
		bookingService.checkIn(customer.getId(), carVehicle.getId(), emptiedSpot.get().getId());
	}

}
