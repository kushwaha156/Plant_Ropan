package com.plant.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plant.Dao.userDao;
import com.plant.entities.Plans;

@Controller
public class PlansAdd {
	
	 @Autowired
	 userDao userdao;
	
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
	
}
