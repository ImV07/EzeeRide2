package com.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.enums.BookingStatus;
import com.project.model.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

	Booking findByCustomer_CustomerId(Long customerId);

	@Query("SELECT b FROM Booking b JOIN FETCH b.vehicle v "
			+ "WHERE v.vehicleId = :vehicleId "
			+ "AND b.status = 'CONFIRM' "
			+ "AND ((b.startDate <= :endDate) AND (b.endDate >= :startDate))")
	List<Booking> findBookingsConflicts(@Param("vehicleId") Long vehicleId, @Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	
	@Query("SELECT b from Booking b WHERE b.startDate = :today AND b.status = :status")
	List<Booking> findBookingStartToday(@Param("today") LocalDate today, @Param("status") BookingStatus status);

	
	@Query("SELECT b from Booking b WHERE b.endDate = :today")
	List<Booking> findBookingEndingToday(@Param("today") LocalDate today);

	
	@Query("SELECT SUM(b.amount) FROM Booking b JOIN b.vehicle v WHERE v.vehicleId = :vehicleId AND b.status = :status AND b.dateOfBooking BETWEEN :startDate AND :endDate")
	Double findConfirmedBookingsByVehicleAndDate(@Param("vehicleId") Long vehicleId,
			@Param("status") BookingStatus status, @Param("startDate") LocalDate start,
			@Param("endDate") LocalDate end);

	
	@Query("SELECT SUM(b.amount) FROM Booking b WHERE b.status = :status AND b.dateOfBooking BETWEEN :startDate AND :endDate")
	Double findConfirmedBookingsByAllVehicleAndDate(@Param("status") BookingStatus status,
			@Param("startDate") LocalDate start, @Param("endDate") LocalDate end);

}
