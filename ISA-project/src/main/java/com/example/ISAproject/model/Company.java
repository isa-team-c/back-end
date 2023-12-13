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
	
	@Column(name = "name",  nullable = false)
	@NotEmpty
	private String name;
	
	@Column(name = "address",  nullable = false)
	@NotEmpty
	private String address;
	
	@Column(name = "description",nullable = false)
	private String description;
	
	@Column(name = "average_rating",  nullable = false)
	private double averageRating;
	
	
	 @ManyToMany
	    @JoinTable(name = "company_equipment",
	               joinColumns = @JoinColumn(name = "company_id"),
	               inverseJoinColumns = @JoinColumn(name = "equipment_id"))
	 private List<Equipment> equipmentList = new ArrayList<>();
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	    private List<CompanyAdministrator> administrators = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Appointment> freeAppointments = new ArrayList<>();
	
    
	
	public Company() { }	

	public Company(long id, @NotEmpty String name, @NotEmpty String address, String description, double averageRating,
			List<CompanyAdministrator> administrators, List<Appointment> freeAppointments, List<Equipment> equipmentList) {
		super();
		Id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
		this.administrators = administrators;
		this.freeAppointments = freeAppointments;
		this.equipmentList= equipmentList;
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

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<CompanyAdministrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(List<CompanyAdministrator> administrators) {
		this.administrators = administrators;
	}

	public List<Appointment> getFreeAppointments() {
		return freeAppointments;
	}

	public void setFreeAppointments(List<Appointment> freeAppointments) {
		this.freeAppointments = freeAppointments;
	}

	

}
