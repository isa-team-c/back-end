package com.example.ISAproject.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.Role;
import com.example.ISAproject.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	public User findOne(Long id) {
		return userRepository.findById(id).orElseGet(null);
	}
	
	public User update(UserDto updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElseGet(null);

        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setSurname(updatedUser.getSurname());
            existingUser.setCity(updatedUser.getCity());
            existingUser.setCountry(updatedUser.getCountry());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setProfession(updatedUser.getProfession());
            existingUser.setCompanyInformation(updatedUser.getCompanyInformation());

            return userRepository.save(existingUser);
        } else {
        	
            throw new EntityNotFoundException("User not found with ID: " + updatedUser.getId());
        }
    }
	
	public User save(UserDto userDto) {
		User user = new User();
		
		// pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
		// treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));		
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setCity(userDto.getCity());
		user.setCountry(userDto.getCountry());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setProfession(userDto.getProfession());
		user.setCompanyInformation(userDto.getCompanyInformation());		
		user.setIsVerified(false);
		Role role = roleService.findByName("REGULAR_USER");
		user.setRole(role);
		
		return this.userRepository.save(user);
	}
	
	public User findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	@Transactional
	public User verifyUser(long id)
	{
		User user = userRepository.getById(id);

	    user.setIsVerified(true);
	    return this.userRepository.save(user);

	}
}