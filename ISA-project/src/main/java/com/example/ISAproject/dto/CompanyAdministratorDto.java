package com.example.ISAproject.dto;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;

public class CompanyAdministratorDto {
	private long id;
    private UserDto user;
    private CompanyDto companyDto;
    private boolean loggedInBefore;

    public CompanyAdministratorDto() {
    }

    public CompanyAdministratorDto(CompanyAdministrator companyAdministrator) {
    	/*if (companyAdministrator != null) {
            this.id = companyAdministrator.getId();
            
            // Check if user is not null before accessing properties
            if (companyAdministrator.getUser() != null) {
                this.user = new UserDto(companyAdministrator.getUser());
            }
        }*/
        //this.company = companyAdministrator.getCompany();
        this.id = companyAdministrator.getId();
        this.user = new UserDto(companyAdministrator.getUser());
        this.loggedInBefore = companyAdministrator.getLoggedInBefore();
        //this.companyDto = new CompanyDto(companyAdministrator.getCompany());
        //this.companyDto = new CompanyDto(companyAdministrator.getCompany());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

   public CompanyDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}
	
	public boolean getLoggedInBefore() {
		return loggedInBefore;
	}
	
	public void setLoggedInBefore(boolean loggedInBefore) {
		this.loggedInBefore = loggedInBefore;
	}
}
    


