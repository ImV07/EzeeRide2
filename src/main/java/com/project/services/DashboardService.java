package com.project.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.DashboardDTO;
import com.project.enums.BookingStatus;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFound;
import com.project.model.Vehicle;
import com.project.repository.BookingRepo;
import com.project.repository.ServicingRepo;
import com.project.repository.VehicleRepo;

@Service
public class DashboardService {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private VehicleRepo vehicleRepo;

	@Autowired
	private ServicingRepo servicingRepo;

// Monthly by Id
	public DashboardDTO getMonthlyReport(Long vehicleId, int year, int month) {
		Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new ResourceNotFound("Vehicle not found"));

		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByVehicleAndDate(vehicleId, BookingStatus.CONFIRM,
				start, end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByVehicleAndDateRange(vehicleId, start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(vehicleId, vehicle.getModel(), safeTotalEarning, safeTotalServiceCost, netEarning);

	}

	
// Annually by Id
	public DashboardDTO getYearlyReport(Long vehicleId, int year) {

		Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new ResourceNotFound("Vehicle not found"));

		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);

		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByVehicleAndDate(vehicleId, BookingStatus.CONFIRM,
				start, end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByVehicleAndDateRange(vehicleId, start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(vehicleId, vehicle.getModel(), safeTotalEarning, safeTotalServiceCost, netEarning);

	}

	
// Quaterly by Id
	public DashboardDTO getQuaterlyReport(Long vehicleId, int year, int q) {

		if (q < 1 || q > 4) {
			throw new BadRequestException("Quarter should be 1, 2, 3 or 4. You entered: " + q);
		}

		LocalDate start;
		LocalDate end;

		switch (q) {

		case 1:
			start = LocalDate.of(year, 1, 1);
			end = LocalDate.of(year, 3, 31);
			break;

		case 2:
			start = LocalDate.of(year, 4, 1);
			end = LocalDate.of(year, 6, 30);
			break;

		case 3:
			start = LocalDate.of(year, 7, 1);
			end = LocalDate.of(year, 9, 30);

		case 4:
			start = LocalDate.of(year, 10, 1);
			end = LocalDate.of(year, 12, 31);

		default:
			throw new BadRequestException("You can't view earnings for a future quarter.");

		}

		Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new ResourceNotFound("Vehicle not found"));

		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByVehicleAndDate(vehicleId, BookingStatus.CONFIRM,
				start, end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByVehicleAndDateRange(vehicleId, start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(vehicleId, vehicle.getModel(), safeTotalEarning, safeTotalServiceCost, netEarning);

	}
	
//////////////////////

//	All monthly
	public DashboardDTO getMonthlyReportForAll(int year, int month) {

		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByAllVehicleAndDate(BookingStatus.CONFIRM, start,
				end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByAllVehicleAndDateRange(start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(null, null, safeTotalEarning, safeTotalServiceCost, netEarning);
	}

	
// All yearly
	public DashboardDTO getYearlyReportForAll(int year) {

		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);

		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByAllVehicleAndDate(BookingStatus.CONFIRM, start,
				end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByAllVehicleAndDateRange(start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(null, null, safeTotalEarning, safeTotalServiceCost, netEarning);

	}

	
//All Quaterly
	public DashboardDTO getQuaterlyReportForAll(int year, int q) {

		if (q < 1 || q > 4) {
			throw new BadRequestException("Quarter should be 1, 2, 3 or 4. You entered: " + q);
		}

		LocalDate start;
		LocalDate end;

		switch (q) {

		case 1:
			start = LocalDate.of(year, 1, 1);
			end = LocalDate.of(year, 3, 31);
			break;

		case 2:
			start = LocalDate.of(year, 4, 1);
			end = LocalDate.of(year, 6, 30);
			break;

		case 3:
			start = LocalDate.of(year, 7, 1);
			end = LocalDate.of(year, 9, 30);

		case 4:
			start = LocalDate.of(year, 10, 1);
			end = LocalDate.of(year, 12, 31);

		default:
			throw new BadRequestException("You can't view earnings for a future quarter.");

		}


		// getting amount from booking
		Double totalEarning = bookingRepo.findConfirmedBookingsByAllVehicleAndDate(BookingStatus.CONFIRM,
				start, end);

		// getting amount from servicing
		Double totalServiceCost = servicingRepo.findByAllVehicleAndDateRange(start, end);

		double safeTotalEarning = totalEarning != null ? totalEarning : 0.0;
	    double safeTotalServiceCost = totalServiceCost != null ? totalServiceCost : 0.0;

		
		// net earnings
		double netEarning = safeTotalEarning - safeTotalServiceCost;

		return new DashboardDTO(null, null, safeTotalEarning, safeTotalServiceCost, netEarning);


	}

}