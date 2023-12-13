package com.example.ISAproject.model;

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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.ISAproject.model.enumerations.ReservationStatus;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "status", nullable = false)
	private ReservationStatus status;
	
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Equipment> equipment  = new ArrayList<>();
	
	@OneToOne
    @JoinColumn(name = "appointment_id")
	private Appointment appointment;
	
	public Reservation() { }

	public Reservation(long id, ReservationStatus status, List<Equipment> equipment, Appointment appointment) {
		super();
		this.id = id;
		this.status = status;
		this.equipment = equipment;
		this.appointment = appointment;
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

	public List<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(List<Equipment> equipment) {
		this.equipment = equipment;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	
}
