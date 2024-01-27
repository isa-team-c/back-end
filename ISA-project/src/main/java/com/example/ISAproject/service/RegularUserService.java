package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.RegularUserDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.repository.RegularUserRepository;

@Service
public class RegularUserService {
	@Autowired
    private RegularUserRepository regularUserRepository;
	
	@Autowired
    private UserService userService;
	
	public RegularUser findById(Long id) {
		return regularUserRepository.findById(id).orElseGet(null);
	}
	
	public RegularUser findByUserId(Long userId) {
		List<RegularUser> regularUsers = regularUserRepository.findAll();
		
		for(RegularUser regularUser : regularUsers) {
			if(regularUser.getUser().getId() == userId) {
				return regularUser;
			}
		}
		
		return null;
	}
	

    public RegularUser updateRegularUser(RegularUserDto updatedUser) {
    	System.out.println("Regular user u servisu nakon penalizacije: id: " + updatedUser.getId() + ", broj penala: " + updatedUser.getPenalties());
    	RegularUser existingUser = regularUserRepository.findById(updatedUser.getId()).orElseGet(null);
    	existingUser.setPenalties(updatedUser.getPenalties());
    	UserDto user = updatedUser.getUser();
        userService.update(user);
        return regularUserRepository.save(existingUser);
        
    }
}
