package com.example.ISAproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.service.CompanyService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/companies")
public class CompanyController {
	
	@Autowired
    private CompanyService companyService;

	@GetMapping("/search")
	public ResponseEntity<List<Company>> searchCompanies(
	    @RequestParam(required = false) String searchTerm
	) {
	    List<Company> companies;

	    if (searchTerm != null && !searchTerm.isEmpty()) {
	        companies = companyService.searchCompanies(searchTerm);
	    } else {
	        companies = companyService.getAllCompanies();
	    }

	    return new ResponseEntity<>(companies, HttpStatus.OK);
	}
}
