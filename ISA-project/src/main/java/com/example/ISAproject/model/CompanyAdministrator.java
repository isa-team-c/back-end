package com.example.ISAproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity

public class CompanyAdministrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true) 
    private Company company;
    
    @JoinColumn(name = "logged_in_before")
	private boolean loggedInBefore;

    
    public CompanyAdministrator() {}
    
    public CompanyAdministrator(Long id, User user, Company company, boolean loggedInBefore) {
        super();
        this.id = id;
        this.user = user;
        this.company = company;
        this.loggedInBefore = loggedInBefore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public boolean getLoggedInBefore() {
		return loggedInBefore;
	}
	
	public void setLoggedInBefore(boolean loggedInBefore) {
		this.loggedInBefore = loggedInBefore;
	}
}
