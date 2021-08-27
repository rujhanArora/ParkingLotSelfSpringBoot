package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.models.Booking;
import com.machineCoding.parkingLot.models.BookingStatus;
import com.machineCoding.parkingLot.models.parkingSpot.ParkingSpot;
import com.machineCoding.parkingLot.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private Map<String, Integer> idToBookingIndex = new HashMap<>();
    private Map<String, List<Integer>> customerIdToBookingsIndex = new HashMap<>();

    @Autowired SpotService spotService;
    @Autowired
    VehicleService vehicleService;

    public Booking checkIn(String customerId, String vehicleId, String parkingSpotId) {
        spotService.bookSpot(parkingSpotId);
        Booking booking = new Booking(customerId, vehicleId, parkingSpotId);
        log.info("Created Booking: {}", booking);
        bookings.add(booking);
        int bookingIndex = bookings.size() - 1;
        customerIdToBookingsIndex.computeIfAbsent(customerId, spots -> new ArrayList<>())
                .add(bookingIndex);
        idToBookingIndex.put(booking.getId(), bookingIndex);
        log.info("booking: {}", booking);
        return booking;
    }

    private Booking getBookingById(String bookingId) {
        return bookings.get(idToBookingIndex.get(bookingId));
    }

    public boolean payForBooking(String bookingId) {
        // Simulating Payment Service here
        Booking booking = getBookingById(bookingId);
        ParkingSpot parkingSpot = spotService.getSpotById(booking.getParkingSpotId());
        booking.setAmountPaid(parkingSpot.getParkingSpotType().getBaseRate());
        booking.setStatus(BookingStatus.PAID);
        log.info("Paid for booking: " + booking);
        return true;
    }

    public boolean checkOut(String bookingId) {
        Booking booking = getBookingById(bookingId);
        ValidationUtil.ensureTrue(booking.getStatus().equals(BookingStatus.PAID), "Payment required for booking id: " + bookingId);
        spotService.vacateSpot(booking.getParkingSpotId());
        booking.setStatus(BookingStatus.CHECKED_OUT);
        return true;
    }
}
