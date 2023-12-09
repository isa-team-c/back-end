package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Role;
import com.example.ISAproject.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	public Role findOne(Long id) {
		return roleRepository.getOne(id);
	}

	public Role findByName(String name) {
		Role role = this.roleRepository.findByName(name);
		return role;
	}
}
