package com.example.ISAproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.model.*;

@Service
public class CompanyAdministratorService {

    @Autowired
    private CompanyAdministratorRepository companyAdministratorRepository;

    @Autowired
    private UserService userService;

    public CompanyAdministrator findById(Long id) {
        return companyAdministratorRepository.findById(id).orElseGet(null);
    }

    public CompanyAdministrator updateCompanyAdministrator(CompanyAdministratorDto updatedUser) {
        CompanyAdministrator existingUser = companyAdministratorRepository.findById(updatedUser.getId()).orElseGet(null);
        UserDto user = updatedUser.getUser();
        userService.update(user);
        return companyAdministratorRepository.save(existingUser);
    }
    
    public CompanyAdministrator createCompanyAdministrator(CompanyAdministratorDto newCompanyAdminDto) {
    	System.out.println("Dobijeno u servisu: " + newCompanyAdminDto.getUser().getName());
        // Perform necessary validations, transformations, and business logic if required
        
        // Create CompanyAdministrator entity from DTO
        CompanyAdministrator newCompanyAdmin = new CompanyAdministrator();
        
        // Populate fields of newCompanyAdmin from newCompanyAdminDto
        com.example.ISAproject.model.User newUser = new com.example.ISAproject.model.User();
        newUser.setEmail(newCompanyAdminDto.getUser().getEmail());
        newUser.setPassword(newCompanyAdminDto.getUser().getPassword());
        newUser.setName(newCompanyAdminDto.getUser().getName());
        newUser.setSurname(newCompanyAdminDto.getUser().getSurname());
        newUser.setCity(newCompanyAdminDto.getUser().getCity());
        newUser.setCountry(newCompanyAdminDto.getUser().getCountry());
        newUser.setPhoneNumber(newCompanyAdminDto.getUser().getPhoneNumber());
        newUser.setProfession(newCompanyAdminDto.getUser().getProfession());
        newUser.setCompanyInformation(newCompanyAdminDto.getUser().getCompanyInformation());
        newUser.setRole(newCompanyAdminDto.getUser().getRole());
        
        newCompanyAdmin.setUser(newUser);
        newCompanyAdmin.setCompany(null);
        
        // Save the new company administrator to the database
        return companyAdministratorRepository.save(newCompanyAdmin);
    }
}