package com.plant.Dao;

import org.springframework.data.repository.CrudRepository;

import com.plant.entities.CustomerMain;

public interface CustomerDao extends CrudRepository<CustomerMain, Integer> {

}
