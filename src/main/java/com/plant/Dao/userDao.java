package com.plant.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plant.entities.user;


public interface userDao extends CrudRepository<user, Integer>{
	
	@Query("select e FROM user e WHERE e.username = :username AND e.password = :password")
	public user finduserNameAndPassword(@Param("username") String username , @Param("password") String password);
}
