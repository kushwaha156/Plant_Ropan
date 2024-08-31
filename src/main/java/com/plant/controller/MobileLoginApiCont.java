package com.plant.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plant.Service.OTPService;
import com.plant.Service.SMSService;

@RestController
@RequestMapping("/MobileLoginApi")
public class MobileLoginApiCont {
	
	 @Autowired
	 private OTPService otpService;
	  
	 @Autowired
	 private SMSService smsService;
	 
	 @PostMapping("/sendOTP")
	 public ResponseEntity<String> sendOTP(@RequestBody Map<String, String> request) {
//	    	System.out.println("sendOTP");
//	        String mobileNumber = request.get("mobileNumber");
//	        String otp = otpService.generateOTP(mobileNumber);
//	        // otpService.sendOTP(mobileNumber, otp);
//	        return "OTP sent successfully! --- " + otp;
	        
	        String mobileNumber = request.get("mobileNumber");
	        
	        if (mobileNumber == null || mobileNumber.isEmpty()) {
	            return ResponseEntity.badRequest().body("Mobile number is required");
	        }
	        
	     
	        String otp = otpService.generateOTP(mobileNumber);

	       
	        smsService.sendOTP(mobileNumber, otp);
	        
	        return ResponseEntity.ok("OTP sent successfully!" + otp);
	    }
}
