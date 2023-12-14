package com.example.ISAproject.dto;

import com.example.ISAproject.model.Administrator;
import com.example.ISAproject.model.CompanyAdministrator;

public class AdministratorDto {
	private Long id;
    private UserDto user;
    private boolean loggedInBefore;
    
    public AdministratorDto()
    {
    	
    }

    public AdministratorDto(Administrator administrator) {
        this.id = administrator.getId();
        this.user = new UserDto(administrator.getUser());
        this.loggedInBefore = administrator.getLoggedInBefore();
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
	
	public boolean getLoggedInBefore() {
		return loggedInBefore;
	}
	
	public void setLoggedInBefore(boolean loggedInBefore) {
		this.loggedInBefore = loggedInBefore;
	}
}
