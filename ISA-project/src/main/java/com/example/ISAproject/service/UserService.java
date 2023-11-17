package com.example.ISAproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.User;
import com.example.ISAproject.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

	public User findOne(Long id) {
		return userRepository.findById(id).orElseGet(null);
	}

}
