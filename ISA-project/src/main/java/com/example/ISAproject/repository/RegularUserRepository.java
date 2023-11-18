package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.RegularUser;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long>{

}
