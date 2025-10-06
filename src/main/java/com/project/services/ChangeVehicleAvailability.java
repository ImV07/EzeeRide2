package com.project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.enums.BookingStatus;
import com.project.model.Booking;
import com.project.model.Vehicle;
import com.project.repository.BookingRepo;
import com.project.repository.VehicleRepo;

@Service
public class ChangeVehicleAvailability {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private VehicleRepo vehicleRepo;

	@Transactional
	@Scheduled(cron = "00 00 11 * * ?")
	public void updateVehicleAvailability() {
		LocalDate today = LocalDate.now();

		// update vehicles to unavailable
		List<Booking> bookingsZero = bookingRepo.findBookingStartToday(today,BookingStatus.CONFIRM);
		
		
		for (Booking b0 : bookingsZero) {

			List<Vehicle> vehicles = b0.getVehicle();

			for (Vehicle vehicle : vehicles) {
				
				if (vehicle.isAvailable()) {
					vehicle.setAvailable(false);
					vehicleRepo.save(vehicle);
				}
			}

		}

		// update vehicles to available
		List<Booking> bookingsOne = bookingRepo.findBookingEndingToday(today);

		for (Booking b1 : bookingsOne) {

			List<Vehicle> vehicles = b1.getVehicle();

			for (Vehicle vehicle : vehicles) {
				if (!vehicle.isAvailable()) {
					vehicle.setAvailable(true);
					vehicleRepo.save(vehicle);
				}
			}

		}
	}
}
