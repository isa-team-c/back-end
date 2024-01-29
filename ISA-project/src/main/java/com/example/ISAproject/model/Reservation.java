package com.example.ISAproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "status", nullable = false)
	private ReservationStatus status;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "qr_code")
	private String qrCode;
	
	//@JsonIgnore 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "reservation_equipment", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
	private Set<Equipment> equipment  = new HashSet<Equipment>();
	
	//@JsonIgnore 
	@OneToOne
    @JoinColumn(name = "appointment_id")
	private Appointment appointment;
	
	//@JsonIgnore
    @ManyToOne
    @JoinTable(name = "reservation_user", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private User user;
  


	public Reservation() { }


	public Reservation(long id, ReservationStatus status, double price, String qrCode, Set<Equipment> equipment, Appointment appointment,
			User user) {
		super();
		this.id = id;
		this.status = status;
		this.price = price;
		this.qrCode = qrCode;
		this.equipment = equipment;
		this.appointment = appointment;
		this.user = user;
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


	public Set<Equipment> getEquipment() {
		return equipment;
	}


	public void setEquipment(Set<Equipment> equipment) {
		this.equipment = equipment;
	}


	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
