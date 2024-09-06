package com.plant.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plant.entities.CustomerMain;
import java.util.List;


public interface CustomerDao extends CrudRepository<CustomerMain, Integer> {
  @Query("select e FROM CustomerMain e where e.mobileNumber=:mobileNumber")
  public List<CustomerMain>findMobileNumber(@Param("mobileNumber")String mobileNumber);
}
