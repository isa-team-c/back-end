package com.example.ISAproject.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.example.ISAproject.model.enumerations.UserRole;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email", unique = true, nullable = false)
	@Email(message = "Email format not correct")
    @Pattern(
        regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        message = "Email format nije ispravan"
    )	
	@NotEmpty
	private String email;
	
	@Column(name = "password", nullable = false)
	@NotEmpty
	private String password;
	
	@Column(name = "name", nullable = false)
	@NotEmpty
	private String name;
	
	@Column(name = "surname", nullable = false)
	@NotEmpty
	private String surname;
	
	@Column(name = "city", nullable = false)
	@NotEmpty
	private String city;
	
	@Column(name = "country", nullable = false)
	@NotEmpty
	private String country;
	
	@Column(name = "phone_number", nullable = false)
	@NotEmpty
	private String phoneNumber;
	
	@Column(name = "profession", nullable = false)
	@NotEmpty
	private String profession;
	
	@Column(name = "company_information", nullable = false)
	@NotEmpty
	private String companyInformation;
	
	@Column(name = "role", nullable = false)
	private UserRole role;
	
	public User() {

	}


	public User(long id,
			@Email(message = "Email format not correct") @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email format nije ispravan") @NotEmpty String email,
			@NotEmpty String password, @NotEmpty String name, @NotEmpty String surname, @NotEmpty String city,
			@NotEmpty String country, @NotEmpty String phoneNumber, @NotEmpty String profession,
			@NotEmpty String companyInformation, UserRole role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.profession = profession;
		this.companyInformation = companyInformation;
		this.role = role;
	}





	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCompanyInformation() {
		return companyInformation;
	}
	public void setCompanyInformation(String companyInformation) {
		this.companyInformation = companyInformation;
	}	
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}	
	
	
}

