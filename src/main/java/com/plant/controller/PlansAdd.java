package com.plant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plant.Dao.CustomerDao;
import com.plant.Dao.userDao;
import com.plant.entities.AgentMain;
import com.plant.entities.Plans;

@Controller
public class PlansAdd {
	
	 @Autowired
	 userDao userdao;
	 
	 @Autowired
	 private CustomerDao customerDao;
	 
	 @PostMapping("/addPlans")
	 @ResponseBody
	 public Map<String, String> addPlan(@ModelAttribute Plans plans) {
	     Map<String, String> response = new HashMap<>();
	     try {
	         Plans savedPlan = this.userdao.save(plans);
	         response.put("message", "Plans Add Successfully");
	     } catch (Exception e) {
	         e.printStackTrace();
	         response.put("message", "Error while adding plans");
	     }
	     return response;
	 }
	
	 
	 @GetMapping("/getPlans")
		public ResponseEntity<HashMap<String, Object>> getAllPlans() {
		    HashMap<String, Object> response = new HashMap<>();
		    List<Plans> getPlans = this.customerDao.getallPlans();
		    List<Plans> activePlans = new ArrayList<>();
		    for (Plans pl : getPlans) {
		        if (pl.getIsActive().equals("Yes")) {
		            activePlans.add(pl);
		        }
		    }
		    response.put("All Plans", activePlans);
		    return ResponseEntity.ok(response);
		}
		
		@GetMapping("/monthWiseRecordFetch")
		@ResponseBody
		public List<Plans> monthWiseRecordFetch() {
			List<Plans> getmonthPlans= this.customerDao.getMonthVSDailyfetch("MONTHLY");
			return getmonthPlans;
		}
		
		@GetMapping("/dailyRecordFetch")
		@ResponseBody
		public List<Plans> dailyRecordFetch() {
			List<Plans> getdailyPlans= this.customerDao.getMonthVSDailyfetch("DAILY");
			return getdailyPlans;
		}
}
