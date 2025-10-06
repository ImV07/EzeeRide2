package com.project.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.exception.ResourceNotFound;
import com.project.model.Vehicle;
import com.project.repository.VehicleRepo;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepo vehicleRepo;

	private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);

	// add new vehicle
	public Vehicle add(Vehicle newVehicle) {

		logger.info("New Vehicle added with id: " + newVehicle.getVehicleId()+" !!!");
		return vehicleRepo.save(newVehicle);
	}

	// get all vehicle
	public Page<Vehicle> getAll(int page,int size) {
		Pageable pageable = PageRequest.of(page, size);
		return vehicleRepo.findAll(pageable);
	}

	// get by id
	public Vehicle getById(Long id) {
		return vehicleRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Vehicle not exist with Vehicle Id: "+id));
	}

	// delete by id
	public String vehicleDelete(Long id) {
		
		if(!vehicleRepo.existsById(id)) {
			throw new ResourceNotFound("Vehicle not found !!");
		}
		vehicleRepo.deleteById(id);

		logger.info("Vehicle removed with id: " + id +" !!!");

		return "vehicle deleted with id " + id;
	}

	// update by id
	public Vehicle updateVehicle(Long id, Vehicle newDetails) {

		Vehicle existing = vehicleRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Vehicle not exist with Vehicle Id: "+id));

		if (newDetails.getCity() != null) {
			existing.setCity(newDetails.getCity());
		}
		if (newDetails.getRegistrationNo() != null) {
			existing.setRegistrationNo(newDetails.getRegistrationNo());
		}
		if (newDetails.getBrand() != null) {
			existing.setBrand(newDetails.getBrand());
		}
		if (newDetails.getModel() != null) {
			existing.setModel(newDetails.getModel());
		}
		if (newDetails.getType() != null) {
			existing.setType(newDetails.getType());
		}
		if (newDetails.getColor() != null) {
			existing.setColor(newDetails.getColor());
		}
		if (newDetails.getCapacity() != null) {
			existing.setCapacity(newDetails.getCapacity());
		}
		if (newDetails.getYearOfManufacture() != null) {
			existing.setYearOfManufacture(newDetails.getYearOfManufacture());
		}
		if (newDetails.getChargePerDay() != 0.0) {
			existing.setChargePerDay(newDetails.getChargePerDay());
		}
		if (newDetails.getVehicleCondition() != null) {
			existing.setVehicleCondition(newDetails.getVehicleCondition());
		}
		if (newDetails.getInsuranceDate() != null) {
			existing.setInsuranceDate(newDetails.getInsuranceDate());
		}
		if (newDetails.getFuelType() != null) {
			existing.setFuelType(newDetails.getFuelType());
		}
		if (newDetails.getProvidedKm() != 0.0) {
			existing.setProvidedKm(newDetails.getProvidedKm());
		}
		if (newDetails.isAvailable() != existing.isAvailable()) {
			existing.setAvailable(newDetails.isAvailable());
		}

		logger.info("Vehicle details updated with id: "+existing.getVehicleId());

		
		return vehicleRepo.save(existing);
	}

	// get by statecode
	public List<Vehicle> getByState(String stateCode) {

		return vehicleRepo.findAllByRegistrationNo(stateCode);
	}

}
