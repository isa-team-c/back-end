package com.example.ISAproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Company {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@Column(name = "name", unique = true, nullable = false)
	@NotEmpty
	private String name;
	
	@Column(name = "address", unique = true, nullable = false)
	@NotEmpty
	private String address;
	
	@Column(name = "description", unique = true, nullable = false)
	private String description;
	
	@Column(name = "average_rating", unique = true, nullable = false)
	private double averageRating;
	
	@Column(name = "equipment", unique = true, nullable = false)
	ArrayList<Equipment> equipment;
	//ArrayList<CompanyAdministrator> CompanyAdministrators; //ili drugacije
	//ArrayList<Appointment> Appointments;
	
	public Company() { }	

	public Company(long id, @NotEmpty String name, @NotEmpty String address, String description, double averageRating,
			ArrayList<Equipment> equipment) {
		super();
		Id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
		this.equipment = equipment;
	}


	public long getId() {
		return Id;
	}

	public void setId(long id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(ArrayList<Equipment> equipment) {
		this.equipment = equipment;
	}

}
