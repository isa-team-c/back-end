package com.example.ISAproject.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.ISAproject.model.Company;

public class CompanyDto {
	private long id;
	private String name;
	private String address;
	private String description;
	private Double averageRating;
	private Set<EquipmentDto> equipment;
	private Set<AppointmentDto> appointments;
	
	public CompanyDto(Company company)
	{
		id = company.getId();
		name = company.getName();
		address = company.getAddress();
		description = company.getDescription();
		averageRating = company.getAverageRating();
	}
	public CompanyDto() {
	    }

	}

	public CompanyDto(Company company)
    {
        id = company.getId();
        name = company.getName();
        address = company.getAddress();
        description = company.getDescription();
        averageRating = company.getAverageRating();
	    /*for (Appointment appointment : company.getAppointments()) {
	        this.appointments.add(new AppointmentDto(appointment));
	    }*/
    }


	public CompanyDto(long id, String name, String address, String description, Double averageRating,
			Set<EquipmentDto> equipment, Set<AppointmentDto> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
		this.equipment = equipment;
		this.appointments = appointments;
	}



	public long getId() {
	        return id;
	 }

	 public void setId(long id) {
	        this.id = id;
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

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Set<EquipmentDto> getEquipment() {
		return equipment;
	}

	public void setEquipment(Set<EquipmentDto> equipment) {
		this.equipment = equipment;
	}

	public Set<AppointmentDto> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<AppointmentDto> appointments) {
		this.appointments = appointments;
	}
	
	
}
