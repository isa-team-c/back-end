package com.example.ISAproject.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
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
	
	@Column(name = "average_rating", nullable = true)
	private Double averageRating;
	
	@Column(name = "work_start_time", nullable = true)
    private LocalTime workStartTime;

    @Column(name = "work_end_time", nullable = true)
    private LocalTime workEndTime;

	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "company_equipment", joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
	private Set<Equipment> equipment = new HashSet<Equipment>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "company_appointment", joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"))
	private Set<Appointment> appointments = new HashSet<Appointment>();
		
	
	public Company() { }



	public Company(long id, @NotEmpty String name, @NotEmpty String address, String description, Double averageRating,
			LocalTime workStartTime, LocalTime workEndTime, Set<Equipment> equipment, Set<Appointment> appointments) {
		super();
		Id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageRating = averageRating;
		this.workStartTime = workStartTime;
		this.workEndTime = workEndTime;
		this.equipment = equipment;
		this.appointments = appointments;
	}

	@PrePersist
    public void setDefaultWorkHours() {
        if (workStartTime == null) {
            workStartTime = LocalTime.of(9, 0); // Na primer, 09:00
        }
        if (workEndTime == null) {
            workEndTime = LocalTime.of(17, 0); // Na primer, 17:00
        }
    }


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

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
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
	
		
	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	

	
	public LocalTime getWorkStartTime() {
		return workStartTime;
	}



	public void setWorkStartTime(LocalTime workStartTime) {
		this.workStartTime = workStartTime;
	}



	public LocalTime getWorkEndTime() {
		return workEndTime;
	}



	public void setWorkEndTime(LocalTime workEndTime) {
		this.workEndTime = workEndTime;
	}



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
