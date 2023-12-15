package com.example.ISAproject.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.CompanyAdministrator;

public class AppointmentDto {
	private long id;
    private CompanyAdministratorDto companyAdministrator;
	private LocalDateTime startDate;
	private int duration;
    private Boolean isFree;
    
    public AppointmentDto() {
    	
    }
    
	public AppointmentDto(long id, CompanyAdministratorDto companyAdministrator, LocalDateTime startDate, int duration,
			Boolean isFree) {
		super();
		this.id = id;
		this.companyAdministrator = companyAdministrator;
		this.startDate = startDate;
		this.duration = duration;
		this.isFree = isFree;
	} 
	
	public AppointmentDto(Appointment appointment)
	{
		this.id = appointment.getId();
		this.companyAdministrator = new CompanyAdministratorDto(appointment.getCompanyAdministrator());
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

	public CompanyAdministratorDto getCompanyAdministrator() {
		return companyAdministrator;
	}

	public void setCompanyAdministrator(CompanyAdministratorDto companyAdministrator) {
		this.companyAdministrator = companyAdministrator;
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
