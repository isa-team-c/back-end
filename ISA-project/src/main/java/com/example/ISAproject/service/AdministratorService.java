package com.example.ISAproject.service;

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
import com.example.ISAproject.repository.AdministratorRepository;

@Service
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;
	
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
        
        return administratorRepository.save(newAdmin);
    }
}
