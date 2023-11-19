package com.example.ISAproject.dto;

import com.example.ISAproject.model.Company;

public class CompanyDto {
	private String name;
	private String address;
	private String description;
	private double averageRating;
	
	public CompanyDto() {

	}

	public CompanyDto(Company company) {
		this(company.getName(), company.getAddress(), company.getDescription(), company.getAverageRating());
	}

	public CompanyDto(String name, String address, String description, double averageRating) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
}
