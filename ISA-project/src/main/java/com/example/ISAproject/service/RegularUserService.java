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
	
	public void deletePenalties() {
		List<RegularUser> users = regularUserRepository.findAll();
		for(RegularUser user: users) {
			user.setPenalties(0);
			RegularUserDto userDto = new RegularUserDto(user);
			updateRegularUser(userDto);
		}
	}
	

    public RegularUser updateRegularUser(RegularUserDto updatedUser) {
    	RegularUser existingUser = regularUserRepository.findById(updatedUser.getId()).orElseGet(null);
    	UserDto user = updatedUser.getUser();
        userService.update(user);
        return regularUserRepository.save(existingUser);
        
    }
}
