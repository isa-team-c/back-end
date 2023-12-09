package com.example.ISAproject.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="users")
public class User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email", unique = true, nullable = false)
	@Email(message = "Email format not correct")
    @Pattern(
    	regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
        message = "Email format nije ispravan"
    )	
	@NotEmpty
	private String email;
	
	@JsonIgnore
	@Column(name = "password", nullable = false)
	@NotEmpty
	private String password;
	
	@Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
	
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
	
	@ManyToOne()
	@JoinColumn(name = "role_id")
    private Role role;
	
	@Column(name = "is_verified")
	private Boolean isVerified;


	public User() {

	}


	public User(long id,
			@Email(message = "Email format not correct") @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Email format nije ispravan") @NotEmpty String email,
			@NotEmpty String password, @NotEmpty String name, @NotEmpty String surname, @NotEmpty String city,
			@NotEmpty String country, @NotEmpty String phoneNumber, @NotEmpty String profession,
			@NotEmpty String companyInformation, Role role, Boolean isVerified) {
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
		this.isVerified = isVerified;
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
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
		this.password = password;
	}
    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}		
	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}


	@JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = new ArrayList<>();
	    roles.add(this.role);
	    return roles;
    }

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public String getUsername() {
		return email;
	}


	@Override
	public boolean isEnabled() {
		return isVerified;
	}	
	
}

