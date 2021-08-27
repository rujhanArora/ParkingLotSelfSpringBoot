package com.machineCoding.parkingLot;

import com.machineCoding.parkingLot.DTOs.CustomerDTO;
import com.machineCoding.parkingLot.models.*;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpotType;
import com.machineCoding.parkingLot.models.parkingSpot.SpotFindStrategy;
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
	@Autowired
	FloorService floorService;

	@Test
	void contextLoads() {
		ParkingLot parkingLot = parkingLotService.createParkingLot("ABC corp", 5);
		Assertions.assertThat(parkingLot.getName().equals("ABC corp"));
		ParkingLotFloor firstFloor = parkingLotService.addFloor(parkingLot.getId(), "Ground Floor", 2);
		try {
			parkingLotService.addFloor(parkingLot.getId(), "Ground Floor", 5);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		Assertions.assertThat(parkingLot.getFloors().size() == 1);

		ParkingSpot carSpot1 = floorService.addSpot(ParkingSpotType.CAR, firstFloor.getId());
		ParkingSpot carSpot2 = floorService.addSpot(ParkingSpotType.CAR, firstFloor.getId());
		try {
			spotService.addSpot(ParkingSpotType.BIKE, firstFloor.getId());
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		Assertions.assertThat(carSpot1.getId().equals(spotService.getSpotById(carSpot1.getId())));
		ParkingSpot firstBookingCarSlot = spotService.getSpot(VehicleType.CAR, SpotFindStrategy.FIRST_EMPTY).get();
		Assertions.assertThat(carSpot1.getId().equals(firstBookingCarSlot.getId()));

		Customer customer = customerService.addCustomer(CustomerDTO.builder().email("rj").name("Rj Arora").build());
		Vehicle carVehicle = vehicleService.addVehicle(Vehicle.builder().vehicleNo("HR15G5772").vehicleType(VehicleType.CAR).customerId(customer.getId()).build());
		Assertions.assertThat(carVehicle.getId().equals(vehicleService.filterVehicles(VehicleFilter.builder().vehicleNo(carVehicle.getVehicleNo()).build()).get(0).getId()));

		Booking carBooking = bookingService.checkIn(customer.getId(), carVehicle.getId(), firstBookingCarSlot.getId());
		Assertions.assertThat(spotService.getSpotById(firstBookingCarSlot.getId()).getSpotStatus().equals(SpotStatus.BOOKED));

		ParkingSpot secondEmptyCarSpot = spotService.getSpot(VehicleType.CAR, SpotFindStrategy.FIRST_EMPTY).get();
		Assertions.assertThat(carSpot2.getId().equals(secondEmptyCarSpot.getId()));
		try {
			bookingService.checkIn(customer.getId(), carVehicle.getId(), firstBookingCarSlot.getId());
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
		Optional<ParkingSpot> emptiedSpot = spotService.getSpot(VehicleType.CAR, SpotFindStrategy.FIRST_EMPTY);
		Assertions.assertThat(emptiedSpot.isPresent());
		bookingService.checkIn(customer.getId(), carVehicle.getId(), emptiedSpot.get().getId());
	}

}
