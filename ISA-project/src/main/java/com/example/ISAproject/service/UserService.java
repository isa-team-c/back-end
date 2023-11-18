package com.example.ISAproject.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.User;
import com.example.ISAproject.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

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

}
