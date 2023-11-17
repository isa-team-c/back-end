package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
