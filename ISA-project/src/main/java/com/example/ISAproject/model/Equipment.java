package com.example.ISAproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
public class Equipment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", unique = true, nullable = false)
	@NotEmpty
	private String name;
	
	@Column(name = "type", nullable = false)
	@NotEmpty
	private String type;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "quantity", nullable = false)
	@NotEmpty
	private int quantity;
	
	@ManyToOne
    @JoinColumn(name = "company_id") // Assuming the column name in Equipment table for the relationship
    private Company company;
	
	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	
	public Equipment() { }



	public Equipment(long id, @NotEmpty String name, @NotEmpty String type, String description, @NotEmpty int quantity,
			Company company, Reservation reservation) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.quantity = quantity;
		this.company = company;
		this.reservation = reservation;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	
}
