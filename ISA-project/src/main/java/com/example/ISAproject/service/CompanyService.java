package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;

	public List<Company> searchCompanies(String searchTerm) {
	    if (searchTerm != null) {
	        return companyRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(searchTerm, searchTerm);
	    } else {
	        return companyRepository.findAll();
	    }
	}
	
	public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
