package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.ServicingDTO;
import com.project.model.Servicing;
import com.project.services.ServicingService;

@RestController
@RequestMapping("/api/service")
public class ServicingController {

	@Autowired
	private ServicingService service;
	
	
	@PostMapping("/{vid}")
	public ServicingDTO RestPost(@PathVariable Long vid,@RequestBody Servicing details) {
		return service.addToService(vid,details);
	}
	
	@GetMapping
	public List<ServicingDTO> getAll() {
		return service.getAllServiceDetails();
	}
	
	@GetMapping("/{sid}")
	public Servicing get(@PathVariable Long sid) {
		return service.getById(sid);
	}
	
	@PatchMapping("/update/{sid}")
	public ServicingDTO patch(@PathVariable Long sid,@RequestBody Servicing updatedDetails ) {
		return service.update(sid,updatedDetails);
	}
	
	
}
