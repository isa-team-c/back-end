package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	List<Company> findAll();
}
