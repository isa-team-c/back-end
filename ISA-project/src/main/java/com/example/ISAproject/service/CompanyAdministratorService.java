package com.example.ISAproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.model.*;

@Service
public class CompanyAdministratorService {

    @Autowired
    private CompanyAdministratorRepository companyAdministratorRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
	private RoleService roleService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

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
    	
    	CompanyAdministrator newCompanyAdmin = new CompanyAdministrator();
        
        com.example.ISAproject.model.User newUser = new com.example.ISAproject.model.User();
        newUser.setEmail(newCompanyAdminDto.getUser().getEmail());
        newUser.setPassword(passwordEncoder.encode(newCompanyAdminDto.getUser().getPassword()));
        newUser.setName(newCompanyAdminDto.getUser().getName());
        newUser.setSurname(newCompanyAdminDto.getUser().getSurname());
        newUser.setCity(newCompanyAdminDto.getUser().getCity());
        newUser.setCountry(newCompanyAdminDto.getUser().getCountry());
        newUser.setPhoneNumber(newCompanyAdminDto.getUser().getPhoneNumber());
        newUser.setProfession(newCompanyAdminDto.getUser().getProfession());
        newUser.setCompanyInformation(newCompanyAdminDto.getUser().getCompanyInformation());
        Role role = roleService.findByName("ROLE_COMPANY_ADMIN");
        newUser.setRole(role);
        newUser.setIsVerified(true);
        
        newCompanyAdmin.setUser(newUser);
        newCompanyAdmin.setCompany(null);
        
        return companyAdministratorRepository.save(newCompanyAdmin);
    }
}