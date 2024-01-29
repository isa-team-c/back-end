package com.example.ISAproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.ISAproject.model.enumerations.ReservationStatus;

@Entity
public class EquipmentQuantity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="equipment_id")
    private long equipmentId;

    @Column(name= "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;   

	public EquipmentQuantity() {
		super();
	}

	public EquipmentQuantity(long id, long equipmentId, Integer quantity, Reservation reservation) {
		super();
		this.id = id;
		this.equipmentId = equipmentId;
		this.quantity = quantity;
		this.reservation = reservation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
    
}
