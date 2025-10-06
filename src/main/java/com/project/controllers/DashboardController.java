package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.DashboardDTO;
import com.project.services.DashboardService;

@RestController
@RequestMapping("api/report")
public class DashboardController {

	@Autowired
	private DashboardService service;
	
	@GetMapping("/monthly/{vid}/{year}/{month}")
	public DashboardDTO monthly(@PathVariable Long vid,@PathVariable int year,@PathVariable int month) {
		return service.getMonthlyReport(vid,year,month);
		
	}
	
	@GetMapping("/year/{vid}/{year}")
	public DashboardDTO yearly(@PathVariable Long vid,@PathVariable int year) {
		return service.getYearlyReport(vid,year);
		
	}
	
	@GetMapping("/quater/{vid}/{year}/{q}")
	public DashboardDTO quaterly(@PathVariable Long vid,@PathVariable int year,@PathVariable int q) {
		return service.getQuaterlyReport(vid,year,q);
		
	}
	
	@GetMapping("/byMonthForAll/{year}/{month}")
	public DashboardDTO monthlyAll(@PathVariable int year,@PathVariable int month) {
		return service.getMonthlyReportForAll(year,month);
		
	}
	
	@GetMapping("/byYearForAll/{year}")
	public DashboardDTO monthlyAll(@PathVariable int year) {
		return service.getYearlyReportForAll(year);
		
	}
	
	@GetMapping("/byQuaterForAll/{year}/{q}")
	public DashboardDTO quaterlyAll(@PathVariable int year,@PathVariable int q) {
		return service.getQuaterlyReportForAll(year,q);
		
	}
	
	
}
