package com.example.ISAproject.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;

public class ReservationForCompanyDto {
	
	private long id;
	private ReservationStatus status;	
	private double price;
	private String qrCode;
	
	
	public ReservationForCompanyDto(long id, ReservationStatus status, double price, String qrCode) {
		super();
		this.id = id;
		this.status = status;
		this.price = price;
		this.qrCode = qrCode;
	
	}
	
	public ReservationForCompanyDto(Reservation reservation)
	{
		this.id = reservation.getId();
		this.status = reservation.getStatus();
		this.price = reservation.getPrice();
		this.qrCode = reservation.getQrCode();
	    
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



}
