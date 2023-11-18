package com.example.ISAproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
}
