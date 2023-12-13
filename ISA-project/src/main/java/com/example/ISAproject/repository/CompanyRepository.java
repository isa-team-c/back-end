package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
}
