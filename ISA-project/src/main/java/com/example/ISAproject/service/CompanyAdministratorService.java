package com.example.ISAproject.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.ISAproject.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.UserRepository;
import com.example.ISAproject.model.*;
import com.example.ISAproject.model.Company;


@Service
public class CompanyAdministratorService {

    @Autowired
    private CompanyAdministratorRepository companyAdministratorRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
	private RoleService roleService;
    
    @Autowired
	private UserRepository userRepository;
    
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
        newCompanyAdmin.setLoggedInBefore(false);
        
        return companyAdministratorRepository.save(newCompanyAdmin);
    }
    
    public CompanyAdministrator updateCompanyAdministratorForPassword(CompanyAdministratorDto updatedAdminDto) {
		User existingUser = userRepository.findById(updatedAdminDto.getUser().getId())
			    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + updatedAdminDto.getUser().getId()));

			CompanyAdministrator existingAdministrator = companyAdministratorRepository.findById(updatedAdminDto.getId())
			    .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + updatedAdminDto.getId()));
		
		if((existingUser != null) && (existingAdministrator != null)) {
			existingUser.setEmail(updatedAdminDto.getUser().getEmail());
			existingUser.setPassword(passwordEncoder.encode(updatedAdminDto.getUser().getPassword()));
	        existingUser.setName(updatedAdminDto.getUser().getName());
	        existingUser.setSurname(updatedAdminDto.getUser().getSurname());
	        existingUser.setCity(updatedAdminDto.getUser().getCity());
	        existingUser.setCountry(updatedAdminDto.getUser().getCountry());
	        existingUser.setPhoneNumber(updatedAdminDto.getUser().getPhoneNumber());
	        existingUser.setProfession(updatedAdminDto.getUser().getProfession());
	        existingUser.setCompanyInformation(updatedAdminDto.getUser().getCompanyInformation());
	        Role role = roleService.findByName("ROLE_COMPANY_ADMIN");
	        existingUser.setRole(role);
	        existingUser.setIsVerified(true);
	        
	        userRepository.save(existingUser);
	        
	        existingAdministrator.setId(updatedAdminDto.getId());
	        existingAdministrator.setUser(existingUser);
	        existingAdministrator.setLoggedInBefore(updatedAdminDto.getLoggedInBefore());
	        
	        return companyAdministratorRepository.save(existingAdministrator);
		} else {
        	
            throw new EntityNotFoundException("Admin not found with ID: " + updatedAdminDto.getId());
        }
	}
    
    
    public CompanyAdministrator findByUserId(Long userId) {
        return companyAdministratorRepository.findByUserId(userId);
    }

}

  
    

