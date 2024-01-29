package com.example.ISAproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

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
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "quantity", nullable = false)
	@NotNull
	private int quantity;
	
	@Column(name = "reserved_quantity", nullable = false)
	@NotNull
	private int reservedQuantity;
	
	//@Version
	//private Integer version = 0;
	
	public Equipment() { this.reservedQuantity = 0; }


	public Equipment(long id, @NotEmpty String name, @NotEmpty String type, String description, double price, @NotEmpty int quantity, @NotEmpty int reservedQuantity) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.reservedQuantity = reservedQuantity;
		
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
	
	

	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

	public int getReservedQuantity() {
		return reservedQuantity;
	}


	public void setReservedQuantity(int reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}
	

	/*public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}*/


	@Override
	public String toString() {
		return "Equipment [name=" + name + ", type=" + type + ", description=" + description + "]";
	}

	
	
	
}
