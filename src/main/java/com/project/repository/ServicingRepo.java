package com.project.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.model.Servicing;

@Repository
public interface ServicingRepo extends JpaRepository<Servicing, Long> {
	
	@Query("SELECT SUM(s.servicingCost) FROM Servicing s WHERE s.vehicle.vehicleId = :vehicleId AND s.serviceDate BETWEEN :start AND :end")
	Double findByVehicleAndDateRange(Long vehicleId, LocalDate start, LocalDate end);

	@Query("SELECT SUM(s.servicingCost) FROM Servicing s WHERE s.serviceDate BETWEEN :start AND :end")
	Double findByAllVehicleAndDateRange(LocalDate start, LocalDate end);

}
