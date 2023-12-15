package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;

public interface UserRepository extends JpaRepository<User, Long>{
	 List<User> findByRole(UserRole role);
	 //User findUserById(Long useriD);
	User findByEmail(String email);
	User getById(long id);
}
