package com.example.ISAproject.dto;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;

public class EquipmentDto {
	private long id;
	private String name;
	private String type;
	private String description;
	private int quantity;
	
	public EquipmentDto(Equipment equipment)
	{
		id = equipment.getId();
		name = equipment.getName();
		type = equipment.getType();
		description = equipment.getDescription();
		quantity = equipment.getQuantity();
	}	

	public EquipmentDto(long id, String name, String type, String description, int quantity) {
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
