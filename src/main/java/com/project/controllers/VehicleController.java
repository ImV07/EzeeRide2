package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Vehicle;
import com.project.services.VehicleService;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService service;
	
	@PostMapping
	public Vehicle postVehicle(@RequestBody Vehicle newVehicle) {
		return service.add(newVehicle);
	}
	
	//	get all
	@GetMapping
	public Page<Vehicle> restGetALL(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "4") int size) {
		return service.getAll(page,size);
	}
	
	//	get by regNo{for state}
	@GetMapping("/state")
	public List<Vehicle> restGetByState(@RequestParam String stateCode) {
		return service.getByState(stateCode);
	}
	
	//	get by vehicleid
	@GetMapping("/{id}")
	public Vehicle restGetById(@PathVariable Long id) {
		return service.getById(id);
	}
	
	//	remove by id
	@DeleteMapping("/remove/{id}")
	public String restDeleteById(@PathVariable Long id) {
		return service.vehicleDelete(id);
	}
	
	//	patch update by id
	@PatchMapping("/update/{id}")
	public Vehicle restupdateById(@PathVariable Long id, @RequestBody Vehicle newDetails) {
		return service.updateVehicle(id,newDetails);
	}
	
}
