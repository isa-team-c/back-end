package com.example.ISAproject.dto;

import com.example.ISAproject.model.Company;

public class CompanyDto {
	private long id;
	private String name;
	private String address;
	private String description;
	private Double averageRating;
	
	public CompanyDto() {

	}

	public CompanyDto(Company company)
    {
        id = company.getId();
        name = company.getName();
        address = company.getAddress();
        description = company.getDescription();
        averageRating = company.getAverageRating();
    }

	 public CompanyDto(long id, String name, String address, String description, double averageRating) {
	        super();
	        this.id = id;
	        this.name = name;
	        this.address = address;
	        this.description = description;
	        this.averageRating = averageRating;
	 }
	 
	 /*
	 public CompanyDto(Company company) {
			this(company.getName(), company.getAddress(), company.getDescription(), company.getAverageRating());
		}
		*/
		

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

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
}
