package com.example.ISAproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.User;
import com.example.ISAproject.service.CompanyService;
import com.example.ISAproject.service.EquipmentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/companies")
public class CompanyController {

	@Autowired
    private CompanyService companyService;
	
	@Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
	
	@GetMapping("/all")
	public ResponseEntity<List<CompanyDto>> getAllCompanies() {

		List<Company> companies = companyService.getAllCompanies();

		if ( companies == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<CompanyDto> companyDtos = companies.stream()
                .map(CompanyDto::new) 
                .collect(Collectors.toList());

        return new ResponseEntity<>(companyDtos, HttpStatus.OK); 
	}
}
