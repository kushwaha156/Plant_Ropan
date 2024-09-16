package com.plant.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plant.entities.AgentMain;
import com.plant.entities.CustomerMain;
import java.util.List;


public interface CustomerDao extends CrudRepository<CustomerMain, Integer> {
	
	
	@Query("select e FROM CustomerMain e WHERE e.emailId = :emailId OR e.mobileNumber = :mobileNumber")
	public CustomerMain findEmailAndMobileCus(@Param("emailId") String emailId , @Param("mobileNumber") String mobileNumber);
	
  @Query("select e FROM CustomerMain e where e.mobileNumber=:mobileNumber")
  public List<CustomerMain>findMobileNumber(@Param("mobileNumber")String mobileNumber);
}
