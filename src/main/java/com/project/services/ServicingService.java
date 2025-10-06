package com.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.ServicingDTO;
import com.project.exception.ResourceNotFound;
import com.project.model.Servicing;
import com.project.model.Vehicle;
import com.project.repository.ServicingRepo;
import com.project.repository.VehicleRepo;

@Service
public class ServicingService {

	
	@Autowired
	private ServicingRepo servicingRepo;

	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	private static final Logger logger=LoggerFactory.getLogger(ServicingService.class);
	
	//	add vehicle to service with vehicleid
	public ServicingDTO addToService(Long vid, Servicing details) {
		
		Vehicle vehicle=vehicleRepo.findById(vid).orElseThrow(()-> new ResourceNotFound("vehicle not found!!!"));
		
		if(!vehicle.isAvailable()) {
			throw new ResourceNotFound("vehicle is (in use /in service)");
		}
		
		vehicle.setAvailable(false);
		vehicleRepo.save(vehicle);
		
		logger.info("vehicle with id "+vid+" is sent to servicing");
		
		details.setVehicles(vehicle);
		servicingRepo.save(details);
		return mapper.map(details, ServicingDTO.class);
	}

	
	//	get all service details
	public List<ServicingDTO> getAllServiceDetails() {
		return servicingRepo.findAll()
				.stream()
		        .map(service -> mapper.map(service, ServicingDTO.class))
		        .collect(Collectors.toList());
	}

	
	//	get by service id
	public Servicing getById(Long sid) {
		return servicingRepo.findById(sid).orElseThrow(()->new ResourceNotFound("Servicing id not present!!!"));
	}

	
	//	patch update by serviceid
	public ServicingDTO update(Long sid, Servicing updatedDetails) {
		Servicing existingService= servicingRepo.findById(sid).orElseThrow(()->new ResourceNotFound("servicing not found with id"+sid));
		
		if(updatedDetails.getServiceDescription() !=null) {
			existingService.setServiceDescription(updatedDetails.getServiceDescription());
		}
		if(updatedDetails.getServicingCost() !=0.0) {
			existingService.setServicingCost(updatedDetails.getServicingCost());
		}
		if(updatedDetails.getRemarks() !=null) {
			existingService.setRemarks(updatedDetails.getRemarks());
		}
		if(updatedDetails.getVehicles() !=null) {
			existingService.setVehicles(updatedDetails.getVehicles());
		}
		
		existingService.setServiceStatus(updatedDetails.isServiceStatus());
		existingService.setPaymentStatus(updatedDetails.isPaymentStatus());
		
		Vehicle vehicle = existingService.getVehicles();
	    if (!existingService.isServiceStatus() && existingService.isPaymentStatus()) {
	        vehicle.setAvailable(true);
	    }
	    
	    logger.info("Updated servicing and vehicle availability for Vehicle ID: " + vehicle.getVehicleId());
	    
	    servicingRepo.save(existingService);
	    return mapper.map(existingService, ServicingDTO.class);
	}
	
	
}
