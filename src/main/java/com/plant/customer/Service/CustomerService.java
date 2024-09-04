package com.plant.customer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plant.Dao.CustomerDao;
import com.plant.entities.CustomerMain;

@Service
public class CustomerService {
 @Autowired
private CustomerDao customerDao;
 public CustomerMain saveCustomerProfile(CustomerMain customerMain) {
	return  customerDao.save(customerMain);
 }
}
