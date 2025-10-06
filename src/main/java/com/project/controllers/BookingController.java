package com.project.controllers;

import com.project.dto.AvailableVehicleDTO;
import com.project.dto.BookingDTO;
import com.project.dto.PaymentUpdateDTO;
import com.project.dto.VehicleDTO;
import com.project.enums.BookingStatus;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFound;
import com.project.model.Booking;
import com.project.model.Customer;
import com.project.repository.BookingRepo;
import com.project.security.SecurityUtil;
import com.project.services.BookingService;
import com.project.services.EmailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private EmailService emailSender;

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

//////////////////////////
	// Get All Booking
	@GetMapping
	public Page<Booking> restGetALL(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "4") int size) {
		return bookingService.getAll(page,size);
	}
	
	
//////////////////////////

	// Fetch vehicles for destination
	@PostMapping("/{cid}")
	public AvailableVehicleDTO initiateBooking(@PathVariable Long cid, @RequestBody Booking basicDetails) {
		return bookingService.initiateBooking(cid, basicDetails);
	}

//////////////////////////
	
	// customer select's vehicles for booking
	@PostMapping("/select-vehicle/{bid}")
	public BookingDTO selectVehicle(@PathVariable Long bid, @RequestBody Booking bookingDetails) {
		return bookingService.vehicleSelection(bid, bookingDetails);
	}

//////////////////////////

	
	// [confirm after pay] / [cancel with reason]
	@Transactional
	@PatchMapping("/status/{id}")
	public String updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentUpdateDTO paymentUpdate) {

		Booking booking = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Booking not found with booking ID: " + id));

		if(booking.getStatus()==BookingStatus.CANCELED) {
			throw new BadRequestException("BOOKING IS CANCELLED, FURTHER PROCESS NOT POSSIBLE !!!");
		}
		
		booking.setPaymentStatus(paymentUpdate.isPaymentStatus());

		String toEmail = booking.getCustomer().getEmail();

		SecurityUtil.validateAccess(booking.getCustomer());

		if (paymentUpdate.isPaymentStatus()) {
			booking.setStatus(BookingStatus.CONFIRM);
			booking.setCancelReason(null);

			//booking.getVehicle().forEach(vehicle -> vehicle.setAvailable(false));

			String sub1 = "Booking Confirmed";
			String body1 = booking.getCustomer().getCname() + "\n\nYour booking is confirmed with bookingId: "
					+ booking.getBookingId() + "\nYour customerId is: " + booking.getCustomer().getCustomerId()
					+ "\n\nEzeeRide";

			emailSender.sendEmail(toEmail, sub1, body1);
			logger.info("Confirmation mail sent to customer: " + toEmail);

		} else {

			if (paymentUpdate.getCancelReason() == null) {
				throw new BadRequestException("Cancel reason required");
			}

			booking.setStatus(BookingStatus.CANCELED);
			booking.setCancelReason(paymentUpdate.getCancelReason());
			booking.getVehicle().forEach(vehicle -> vehicle.setAvailable(true));

			String sub2 = "Booking Cancelled";
			String body2 = booking.getCustomer().getCname() + "\n\nYour booking is cancelled with bookingId: "
					+ booking.getBookingId() + "\n\nBooking Amount of Rs" + booking.getAmount()
					+ " refunded successfully !!!" + "\n\nEzeeRide";

			emailSender.sendEmail(toEmail, sub2, body2);
			logger.info("Canceled/Refund mail sent to customer: " + toEmail);

		}

		bookingRepo.save(booking);
		return "Booking updated. Current status: " + booking.getStatus();
	}

//////////////////////////

	
	//	get by bookingId
	@GetMapping("/{bid}")
	public BookingDTO getById(@PathVariable Long bid) {

		Booking existingBooking = bookingRepo.findById(bid)
				.orElseThrow(() -> new ResourceNotFound("Booking not found with booking ID " + bid));

		Customer customer = existingBooking.getCustomer();

		SecurityUtil.validateAccess(customer);

		BookingDTO bookingDTO = modelMapper.map(existingBooking, BookingDTO.class);

		List<VehicleDTO> vehicleDTOs = existingBooking.getVehicle().stream()
				.map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class)).collect(Collectors.toList());

		bookingDTO.setVehicles(vehicleDTOs);
		bookingDTO.setMessage("Your booking information");
		logger.info("ADMIN / User: " + customer.getCname() + " checked booking");
		return bookingDTO;
	}

//////////////////////////

	
	//	update destination by bookingId
	@PatchMapping("/update/destination/{bid}")
	public AvailableVehicleDTO updateDestination(@PathVariable Long bid, @RequestBody Booking updateBooking) {

		Booking existingBooking = bookingRepo.findById(bid)
				.orElseThrow(() -> new ResourceNotFound("Booking not exist with booking id: " + bid));

		SecurityUtil.validateAccess(existingBooking.getCustomer());

		return bookingService.updateDestination(bid, updateBooking);

	}

}