package com.example.ISAproject.dto;

import java.time.LocalDateTime;

import com.example.ISAproject.model.Appointment;

public class AppointmentForCompanyDto {
	
	private long id;

	private LocalDateTime startDate;
	private int duration;
    private Boolean isFree;
    
    public AppointmentForCompanyDto() {
    	
    }
    
	public AppointmentForCompanyDto(long id, LocalDateTime startDate, int duration,
			Boolean isFree) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.duration = duration;
		this.isFree = isFree;
	} 
	
	public AppointmentForCompanyDto(Appointment appointment)
	{
		this.id = appointment.getId();
		
		this.startDate = appointment.getStartDate();
		this.duration = appointment.getDuration();
		this.isFree = appointment.getIsFree();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}


}
