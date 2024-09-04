package com.plant.customer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plant.customer.Service.CusOTPService;
import com.plant.customer.Service.CusSmsService;

@RestController
@RequestMapping("/CusMobLoginApi")
public class CusMobLoginApi {
	@Autowired
	private CusOTPService cusOTPService;
	@Autowired
	private CusSmsService cusSMSService;
	 @PostMapping("/sendOTP")
	 public ResponseEntity<Map<String, String>> sendOTP(@RequestBody Map<String, String> request) {     
		 Map<String, String> response = new HashMap<>();  
		 String mobileNumber = request.get("mobileNumber");
	        
	     if (mobileNumber == null || mobileNumber.isEmpty()) {
	         return ResponseEntity.badRequest().body(Map.of("error", "Mobile number is required"));
	     }
	     String otp = cusOTPService.generateOTP(mobileNumber);

	     cusSMSService.sendOtp(mobileNumber, otp);
	    
	     response.put("message", "OTP sent successfully!");
	    
	     return ResponseEntity.ok(response);
	 }
	 @PostMapping("/verifyOTP")
	    public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody Map<String, String> request) {
		 Map<String, String> response = new HashMap<>();  
	        String mobileNumber = request.get("mobileNumber");
	        String otp = request.get("otp");

	        if (mobileNumber == null || mobileNumber.isEmpty() || otp == null || otp.isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("error","Mobile number and OTP are required"));
	        }

	        boolean isOtpValid = cusOTPService.verifyOtp(mobileNumber, otp);

	        if (isOtpValid) {
	        	response.put("message", "OTP Verified Successfully");
	            return ResponseEntity.ok(response);
	        } else {
	        	response.put("message", "Invalid or Expired OTP");
	        	return ResponseEntity.ok(response);
	        }
	    }

}
