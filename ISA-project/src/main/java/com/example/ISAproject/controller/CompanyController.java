package com.example.ISAproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.service.CompanyService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/company")
public class CompanyController{
	
	private CompanyService companyService;

	@Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
	
	
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody CompanyDto companyDto)
	{
		
		Company company = new Company();
		company.setName(companyDto.getName());
		company.setAddress(companyDto.getAddress());
		company.setDescription(companyDto.getDescription());
		company.setAverageRating(companyDto.getAverageRating());
		
		companyService.save(company);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	

}