package com.project.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dto.AvailableVehicleDTO;
import com.project.dto.BookingDTO;
import com.project.dto.VehicleDTO;
import com.project.enums.BookingStatus;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFound;
import com.project.model.Booking;
import com.project.model.Customer;
import com.project.model.Vehicle;
import com.project.repository.BookingRepo;
import com.project.repository.CustomerRepo;
import com.project.repository.VehicleRepo;

@Service
public class BookingService {

	@Autowired
	private VehicleRepo vehicleRepo;

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ModelMapper modelMapper;
	
/////////////////////
	// Get All
	public Page<Booking> getAll(int page,int size) {
		Pageable pageable = PageRequest.of(page, size);
		return bookingRepo.findAll(pageable);
	}
	
	
/////////////////////

	// Initial booking
		public AvailableVehicleDTO initiateBooking(Long cid,Booking initialDetails) {
		    Customer customer = customerRepo.findById(cid)
		        .orElseThrow(() -> new ResourceNotFound("Customer not found"));

		    long days = ChronoUnit.DAYS.between(initialDetails.getStartDate(), initialDetails.getEndDate());
		    if (days <= 0) throw new IllegalArgumentException("End date must be after start date");

		    List<Vehicle> availableVehicles = vehicleRepo.findAvailableVehiclesByCity(initialDetails.getDestination());
		    
		   List<VehicleDTO> vehicleDTOs = availableVehicles.stream()
		    				 .map(v -> modelMapper.map(v, VehicleDTO.class))
		    				 .toList();
		    				 

		    Booking booking = new Booking();
		    
		    booking.setCustomer(customer);
		    booking.setDateOfBooking(LocalDate.now());
		    booking.setStartDate(initialDetails.getStartDate());
		    booking.setEndDate(initialDetails.getEndDate());
		    booking.setDestination(initialDetails.getDestination());
		    booking.setGroupSize(initialDetails.getGroupSize());
		    
		   bookingRepo.save(booking);
		    
		    AvailableVehicleDTO initialResponse =new AvailableVehicleDTO();
		    initialResponse.setBookingId(booking.getBookingId());
		    initialResponse.setDestination(initialDetails.getDestination());
		    initialResponse.setAvailableVehicles(vehicleDTOs);
		    initialResponse.setMessage("available vehicles for the destination: "+initialDetails.getDestination());

		    return initialResponse;
		}
	

//////////////////

	// Select vehicle from Available Vehicles
	public BookingDTO vehicleSelection(Long bid,Booking bookingDetails) {

		Booking booking=bookingRepo.findById(bid)
				.orElseThrow(() -> new ResourceNotFound("bookinId not (found / registered) in db "));

		// Get vehicle IDs and fetch vehicle objects
		List<Long> vehicleIds = bookingDetails.getVehicle().stream().map(Vehicle::getVehicleId)
				.collect(Collectors.toList());

		List<Vehicle> selectedVehicles = vehicleRepo.findAllById(vehicleIds);

		for (Vehicle vehicle : selectedVehicles) {
	        List<Booking> conflicts = bookingRepo.findBookingsConflicts(
	            vehicle.getVehicleId(),
	            booking.getStartDate(),
	            booking.getEndDate()
	        );

	        if (!conflicts.isEmpty()) {
	            throw new BadRequestException("Vehicle ID " + vehicle.getVehicleId() + 
	                " is already booked from " +
	                conflicts.get(0).getStartDate() + " to " + conflicts.get(0).getEndDate());
	        }
	    }

		// Calculate number of days
		long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
		if (days <= 0) {
			throw new IllegalArgumentException("End date must be after start date");
		}

		double totalAmount = selectedVehicles.stream().mapToDouble(v -> v.getChargePerDay() * days).sum();

		booking.setAmount(totalAmount);
		booking.setVehicle(selectedVehicles);
		Booking savedBooking = bookingRepo.save(booking);

		BookingDTO bookingDTO = modelMapper.map(savedBooking, BookingDTO.class);

		List<VehicleDTO> vehicleDTOList = selectedVehicles.stream()
				.map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class)).collect(Collectors.toList());

		bookingDTO.setVehicles(vehicleDTOList);
		bookingDTO.setMessage("Booking will confirm after payment!!");

		return bookingDTO;
	}

////////////////////

//	update destination and fetch available vehicles
	public AvailableVehicleDTO updateDestination(Long bookingId, Booking newDestination) {

		Booking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFound("booking not found with ID: " + bookingId));


		if (booking.getStatus() != BookingStatus.PENDING) {
			throw new BadRequestException("Destination can only be changed if booking is PENDING.");
		}

		booking.setDestination(newDestination.getDestination());
		booking.setStartDate(newDestination.getStartDate());
		booking.setEndDate(newDestination.getEndDate());
		booking.setGroupSize(newDestination.getGroupSize());
		bookingRepo.save(booking);

		List<Vehicle> availableVehicles = vehicleRepo.findAvailableVehiclesByCity(newDestination.getDestination());
		List<VehicleDTO> vehicleDTOs = availableVehicles.stream()
				.map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class)).collect(Collectors.toList());


		 AvailableVehicleDTO availableVehicle =new AvailableVehicleDTO();		 
		 availableVehicle.setBookingId(bookingId);
		    availableVehicle.setDestination(newDestination.getDestination());
		    availableVehicle.setAvailableVehicles(vehicleDTOs);
		    availableVehicle.setMessage("available vehicles for the destination: "+newDestination.getDestination());

		    return availableVehicle;
	}
	

}
