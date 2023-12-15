package com.example.ISAproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
	
	@Column(name = "quantity", nullable = false)
	@NotEmpty
	private int quantity;
	
	public Equipment() { }


	public Equipment(long id, @NotEmpty String name, @NotEmpty String type, String description, @NotEmpty int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.quantity = quantity;
		
		
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
	
}
