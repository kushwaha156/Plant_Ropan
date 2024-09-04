package com.plant.customer.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plant.customer.Service.CustomerService;
import com.plant.entities.CustomerMain;

@RestController
@RequestMapping("/CusMobCreateProfApi")
public class CusMobCreateProfApi {
	@Autowired
	private CustomerService customerService;
	@PostMapping("/createProfile")
     public ResponseEntity<HashMap<String, CustomerMain>> createProfile(@RequestBody CustomerMain customerMain) {
    	 HashMap<String, CustomerMain>response=new HashMap<>();
    	 CustomerMain saveCustomer= customerService.saveCustomerProfile(customerMain);
    	 response.put("Create Customer Profile" , saveCustomer);
    	 return new ResponseEntity<>(response, HttpStatus.CREATED);
     }
}
