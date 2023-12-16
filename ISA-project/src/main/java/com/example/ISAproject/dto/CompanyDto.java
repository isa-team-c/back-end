package com.example.ISAproject.dto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;

public class CompanyDto {
	private long id;
	private String name;
	private String address;
	private String description;
	private Double averageRating;
	private LocalTime workStartTime;
	private LocalTime workEndTime;
	private Set<EquipmentDto> equipment;
	private Set<AppointmentDto> appointments;
	
	public CompanyDto() {

	}

	public CompanyDto(Company company) {
        id = company.getId();
        name = company.getName();
        address = company.getAddress();
        description = company.getDescription();
        averageRating = company.getAverageRating();
        workStartTime = company.getWorkStartTime();
        workEndTime = company.getWorkEndTime();
    }

	


	public CompanyDto(long id, String name, String address, String description, Double averageRating,
			LocalTime workStartTime, LocalTime workEndTime) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
		this.workStartTime = workStartTime;
		this.workEndTime = workEndTime;
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

	public LocalTime getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(LocalTime workStartTime) {
		this.workStartTime = workStartTime;
	}

	public LocalTime getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(LocalTime workEndTime) {
		this.workEndTime = workEndTime;
	}
	
	
	
}
