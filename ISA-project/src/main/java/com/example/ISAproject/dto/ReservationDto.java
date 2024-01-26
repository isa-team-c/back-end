package com.example.ISAproject.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;

public class ReservationDto {
	private long id;
	private ReservationStatus status;	
	private double price;
	private String qrCode;
	private Set<EquipmentDto> equipment;
	private AppointmentDto appointment;
	private UserDto user;
	
	public ReservationDto() {
		
	}
	
	public ReservationDto(long id, ReservationStatus status, double price, String qrCode, Set<EquipmentDto> equipment,
			AppointmentDto appointment, UserDto user) {
		super();
		this.id = id;
		this.status = status;
		this.price = price;
		this.qrCode = qrCode;
		this.equipment = equipment;
		this.appointment = appointment;
		this.user = user;
	}
	
	public ReservationDto(Reservation reservation)
	{
		this.id = reservation.getId();
		this.status = reservation.getStatus();
		this.price = reservation.getPrice();
		this.qrCode = reservation.getQrCode();
	    this.equipment = new HashSet<>();
	    for (Equipment equipment : reservation.getEquipment()) {
	        this.equipment.add(new EquipmentDto(equipment));
	    }
	    this.appointment = new AppointmentDto(reservation.getAppointment());
	    this.user = new UserDto(reservation.getUser());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Set<EquipmentDto> getEquipment() {
		return equipment;
	}

	public void setEquipment(Set<EquipmentDto> equipment) {
		this.equipment = equipment;
	}

	public AppointmentDto getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentDto appointment) {
		this.appointment = appointment;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}