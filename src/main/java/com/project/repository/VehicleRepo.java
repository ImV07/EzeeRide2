package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long > {

	@Query("SELECT v FROM Vehicle v WHERE UPPER(v.registrationNo) LIKE CONCAT(:stateCode, '%')")
	List<Vehicle> findAllByRegistrationNo(@Param("stateCode") String stateCode);

	@Query("SELECT v FROM Vehicle v WHERE v.city = :destination AND v.available = true")
	List<Vehicle> findAvailableVehiclesByCity(@Param("destination") String destination);

//	void save(List<Vehicle> vehicle);


}
