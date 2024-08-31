package com.plant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/userhome")
	public  String userhome() {
		return "userhome";
	}
	
	
	@GetMapping("/AdminDashboard")
	public  String AdminDashboard() {
		return "AdminDashboard";
	}
	
}