package com.example.ISAproject.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AdministratorDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.RegularUserDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Administrator;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.Role;
import com.example.ISAproject.model.User;
import com.example.ISAproject.repository.AdministratorRepository;
import com.example.ISAproject.repository.UserRepository;

@Service
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	public Administrator createAdministrator(AdministratorDto newAdminDto) {
    	
    	Administrator newAdmin = new Administrator();
        
        com.example.ISAproject.model.User newUser = new com.example.ISAproject.model.User();
        newUser.setEmail(newAdminDto.getUser().getEmail());
        newUser.setPassword(passwordEncoder.encode(newAdminDto.getUser().getPassword()));
        newUser.setName(newAdminDto.getUser().getName());
        newUser.setSurname(newAdminDto.getUser().getSurname());
        newUser.setCity(newAdminDto.getUser().getCity());
        newUser.setCountry(newAdminDto.getUser().getCountry());
        newUser.setPhoneNumber(newAdminDto.getUser().getPhoneNumber());
        newUser.setProfession(newAdminDto.getUser().getProfession());
        newUser.setCompanyInformation(newAdminDto.getUser().getCompanyInformation());
        Role role = roleService.findByName("ROLE_ADMIN");
        newUser.setRole(role);
        newUser.setIsVerified(true);
        
        newAdmin.setUser(newUser);
        
        newAdmin.setLoggedInBefore(false);
        
        return administratorRepository.save(newAdmin);
    }
	
	
	
	public Administrator findOne(Long id) {
		return administratorRepository.findById(id).orElseGet(null);
	}
	
	public Administrator updateAdministrator(AdministratorDto updatedAdminDto) {
		User existingUser = userRepository.findById(updatedAdminDto.getUser().getId()).orElseGet(null);
		Administrator existingAdministrator = administratorRepository.findById(updatedAdminDto.getId()).orElseGet(null);
		
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
	        Role role = roleService.findByName("ROLE_ADMIN");
	        existingUser.setRole(role);
	        existingUser.setIsVerified(true);
	        
	        userRepository.save(existingUser);
	        
	        existingAdministrator.setId(updatedAdminDto.getId());
	        existingAdministrator.setUser(existingUser);
	        existingAdministrator.setLoggedInBefore(updatedAdminDto.getLoggedInBefore());
	        
	        return administratorRepository.save(existingAdministrator);
		} else {
        	
            throw new EntityNotFoundException("Admin not found with ID: " + updatedAdminDto.getId());
        }
	}
}
