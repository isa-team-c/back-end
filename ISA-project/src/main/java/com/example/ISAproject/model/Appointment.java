package com.example.ISAproject.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.example.ISAproject.dto.CompanyAdministratorDto;

@Entity
@Table(name="appointments")
public class Appointment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "administrator_id")
    private CompanyAdministrator companyAdministrator;
		
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "duration", nullable = false)
	private int duration;
	
	@Column(name = "is_free", nullable = false)
    private Boolean isFree;   
	
	
	public Appointment(long id, CompanyAdministrator companyAdministrator, LocalDateTime startDate, int duration,
			Boolean isFree) {
		super();
		this.id = id;
		this.companyAdministrator = companyAdministrator;
		this.startDate = startDate;
		this.duration = duration;
		this.isFree = isFree;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public CompanyAdministrator getCompanyAdministrator() {
		return companyAdministrator;
	}
	public void setCompanyAdministrator(CompanyAdministrator companyAdministrator) {
		this.companyAdministrator = companyAdministrator;
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
