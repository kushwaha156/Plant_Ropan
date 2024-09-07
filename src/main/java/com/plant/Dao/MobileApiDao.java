package com.plant.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plant.entities.AgentMain;

public interface MobileApiDao extends CrudRepository<AgentMain, Integer>{
	

	@Query("select e FROM AgentMain e WHERE e.mobileNumber = :mobileNumber")
	public List<AgentMain> findMobileNumber(@Param("mobileNumber") String mobileNumber );
}
