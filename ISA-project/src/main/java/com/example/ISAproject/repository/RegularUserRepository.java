package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.User;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long>{
	RegularUser findByUser(User user);
}
