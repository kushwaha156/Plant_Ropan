package com.plant.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.plant.Dao.MobileApiDao;
import com.plant.Dao.userDao;
import com.plant.Service.OTPService;
import com.plant.Service.SmsService;
import com.plant.entities.AgentMain;
import com.plant.entities.user;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/MobileLoginApi")
public class MobileLoginApiCont {
	
	 @Autowired
	 private OTPService otpService;
	  
	 @Autowired
	 private SmsService smsService;
	 
	 @Autowired
	 userDao userdao;
	 
	 @Autowired
		private MobileApiDao mobileApiDao;
	 
	 @PostMapping("/sendOTP")
	 public ResponseEntity<Map<String, String>> sendOTP(@RequestBody Map<String, String> request) {     
		 Map<String, String> response = new HashMap<>();  
		 String mobileNumber = request.get("mobileNumber");
	        
	     if (mobileNumber == null || mobileNumber.isEmpty()) {
	         return ResponseEntity.badRequest().body(Map.of("error", "Mobile number is required"));
	     }
	     String otp = otpService.generateOTP(mobileNumber);

	     smsService.sendOtp(mobileNumber, otp);
	    
	     response.put("message", "OTP sent successfully!");
	    
	     return ResponseEntity.ok(response);
	 }
	 
	 
	 @PostMapping("/verifyOTP")
		public ResponseEntity<Map<String, Object>> verifyOTP(@RequestBody Map<String, String> request) {
			Map<String, Object> response = new HashMap<>();
			String mobileNumber = request.get("mobileNumber");
			String otp = request.get("otp");
			List<AgentMain> findMob = mobileApiDao.findMobileNumber(mobileNumber);

			if (mobileNumber == null || mobileNumber.isEmpty() || otp == null || otp.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Mobile number and OTP are required"));
			}

			boolean isOtpValid = otpService.verifyOtp(mobileNumber, otp);

			if (isOtpValid) {
				if (findMob.isEmpty()) {
					response.put("AgentExit", "false");
					response.put("message", "OTP Verified Successfully");
				} else {
					response.put("Object", findMob);
					response.put("AgentExit", "true");
					response.put("message", "OTP Verified Successfully");
				}
				return ResponseEntity.ok(response);
			} else {
				response.put("message", "Invalid or Expired OTP");
				return ResponseEntity.ok(response);
			}
		}
	 
	 @PostMapping("/getPersonalDetailProfileAgent")
		public ResponseEntity<Map<String, String>> getPersonalDetailProfileAgent(@RequestBody AgentMain agentMain) {
		    Map<String, String> response = new HashMap<>();
		    
		    AgentMain findEmailAndMobile = this.userdao.findEmailAndMobileAg(agentMain.getEmailId(), agentMain.getMobileNumber());
		    if(findEmailAndMobile != null) {
		    	if(agentMain.getEmailId().equals(findEmailAndMobile.getEmailId())) {
			    	response.put("message", "Email Already Exit !!");
			    	return ResponseEntity.ok(response);
			    }
		    }
		    if(findEmailAndMobile != null) {
		    	 if(agentMain.getMobileNumber().equals(findEmailAndMobile.getMobileNumber())) {
		 	    	response.put("message", "Mobile Number Exit !!");
		 	    	return ResponseEntity.ok(response);
		 	    }
		    }
			AgentMain savedAgent = this.userdao.save(agentMain);
			String agentID = String.valueOf(savedAgent.getAgentIDPk()); 
			response.put("agentID",agentID );
		    return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getselfiImage")
		public ResponseEntity<Map<String, String>> getselfiImage(@RequestParam("selfiImage") MultipartFile file, @RequestParam("agentID") String agentID) {
		    Map<String, String> response = new HashMap<>();

		    if (file.isEmpty()) {
		        response.put("message", "Please select your selfie image.");
		        return ResponseEntity.badRequest().body(response);
		    }

		    try {
		        final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
		        Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        file.getInputStream().close();
		        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();
		       
		        this.userdao.updateSelfiImage(file.getOriginalFilename(), agentID);
		       
		        response.put("agentID", agentID);
		        response.put("message", "Image uploaded successfully.");
		        response.put("imageUrl", imageUrl);
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        response.put("message", "Something went wrong while uploading the image.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }

		    return ResponseEntity.ok(response);
		}

		@PostMapping("/getaddressDetail")
		public ResponseEntity<Map<String, String>> getaddressDetail(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 String state = request.get("state");
			 String city = request.get("city");
			 String address = request.get("address");
			 String pincode = request.get("pincode");
			 String latitude = request.get("latitude");
			 String longitude = request.get("longitude");
			 
			 this.userdao.updateAddress(state,city,address, pincode,latitude, longitude, AgentIDPk);
			 response.put("agentID",AgentIDPk );
			 response.put("message", "Address Addedd successfully");
			 return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getaddharImage")
		public ResponseEntity<Map<String, String>> getaddharImage(@RequestParam("aadharImg") MultipartFile file, @RequestParam("agentID") String agentID , @RequestParam("aadhaarNumber") String aadhaarNumber) {
		    Map<String, String> response = new HashMap<>();

		    if (file.isEmpty()) {
		        response.put("message", "Please select your Aadhar image.");
		        return ResponseEntity.badRequest().body(response);
		    }

		    try {
		        final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
		        Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        file.getInputStream().close();
		        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();
		       
		        this.userdao.updateaddharImage(file.getOriginalFilename(), aadhaarNumber, agentID);
		       
		        response.put("agentID", agentID);
		        response.put("message", "Image Aadhar uploaded successfully.");
		        response.put("imageUrl", imageUrl);
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        response.put("message", "Something went wrong while uploading the image.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }

		    return ResponseEntity.ok(response);
		}
		

		@PostMapping("/getbankAccount")
		public ResponseEntity<Map<String, String>> getbankAccount(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 String accHolderName = request.get("accHolderName");
			 String accNumber = request.get("accNumber");
			 String bankName = request.get("bankName");
			 String IFSCCode = request.get("IFSCCode");
			 String AccMobNumber = request.get("AccMobNumber");
			
			 this.userdao.updateBankAccount(accHolderName,accNumber,bankName, IFSCCode,AccMobNumber, AgentIDPk);
			 response.put("agentID",AgentIDPk );
			 response.put("message", "Bank Account Addedd successfully");
			 return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getBankAccPassBookImage")
		public ResponseEntity<Map<String, Object>> getBankAccPassBookImage(@RequestParam("BankAccPassBookImage") MultipartFile file, @RequestParam("agentID") String agentID) {
		    Map<String, Object> response = new HashMap<>();
		    if (file.isEmpty()) {
		        response.put("message", "Please select your Bank Pass book image.");
		        return ResponseEntity.badRequest().body(response);
		    }
		    try {
		        final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
		        Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        file.getInputStream().close();

		        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();

		        this.userdao.updateBankPassBookImg(file.getOriginalFilename(), agentID);

		        AgentMain allRecordFind = this.userdao.findAgentID(agentID);
		        response.put("Object", allRecordFind); 
		        response.put("agentID", agentID);
		        response.put("message", "Bank Pass book uploaded successfully.");
		        response.put("imageUrl", imageUrl);
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        response.put("message", "Something went wrong while uploading the image.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }

		    return ResponseEntity.ok(response);
		}
}
