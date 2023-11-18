package com.example.ISAproject.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Company {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@Column(name = "name", unique = true, nullable = false)
	@NotEmpty
	private String name;
	
	@Column(name = "address", unique = true, nullable = false)
	@NotEmpty
	private String address;
	
	@Column(name = "description", unique = true, nullable = false)
	private String description;
	
	@Column(name = "average_rating", nullable = false)
	private double averageRating;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private Set<Equipment> equipment = new HashSet<Equipment>();
	
	//ArrayList<CompanyAdministrator> CompanyAdministrators;
	
	//ArrayList<Appointment> Appointments;
	
	
	
	public Company() { }

	
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public Set<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(Set<Equipment> equipment) {
		this.equipment = equipment;
	}


	
	/*
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Company c = (Company) o;
		return Id != null && Id.equals(c.getId());
	}
	*/
	
	
	
	@Override
	public int hashCode() {
		return 1337;
	}



	@Override
	public String toString() {
		return "Company [Id=" + Id + ", name=" + name + ", address=" + address + ", description=" + description
				+ ", averageRating=" + averageRating + ", equipment=" + equipment + "]";
	}
}
