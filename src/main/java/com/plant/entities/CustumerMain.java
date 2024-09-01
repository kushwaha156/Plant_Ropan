package com.plant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Custumer_Main" , uniqueConstraints = {@UniqueConstraint(columnNames = {"mobileNumber" , "emailId"})})
public class CustumerMain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int primarykey;
	
	@Column(unique = true)
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String emailId;
	@Column(unique = true)
	private String mobileNumber;
}
