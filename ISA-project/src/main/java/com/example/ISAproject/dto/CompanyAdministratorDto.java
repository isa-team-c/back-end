package com.example.ISAproject.dto;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;

public class CompanyAdministratorDto {
    private Long id;
    private UserDto user;
    //private Company company;

    public CompanyAdministratorDto() {
    }

    public CompanyAdministratorDto(CompanyAdministrator companyAdministrator) {
        this.id = companyAdministrator.getId();
        this.user = new UserDto(companyAdministrator.getUser());
        //this.company = companyAdministrator.getCompany();
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
}