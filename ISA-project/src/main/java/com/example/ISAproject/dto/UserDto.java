package com.example.ISAproject.dto;

import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;

public class UserDto {
	private long id;
	private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String country;
    private String phoneNumber;
    private String profession;
    private String companyInformation;
    private UserRole role;
    private String confirmationPassword;
    private Boolean isVerified;

    public UserDto() {

	}
	
	public UserDto(User user) {
		id = user.getId();		
		email = user.getEmail();
		password = user.getPassword();
		name = user.getName();
		surname = user.getSurname();
		city = user.getCity();
		country = user.getCountry();
		phoneNumber = user.getPhoneNumber();
		profession = user.getProfession();
		companyInformation = user.getCompanyInformation();
		role = user.getRole();
		confirmationPassword = "";
		setIsVerified(false);
	}

	public UserDto(long id, String email, String password, String name, String surname, String city, String country,
			String phoneNumber, String profession, String companyInformation, UserRole role, String confirmationPassword, Boolean isVerified) {
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
		this.confirmationPassword = confirmationPassword;
		this.setIsVerified(isVerified);
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

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
 
	
}


