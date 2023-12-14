package com.example.ISAproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AdministratorDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.service.AdministratorService;
import com.example.ISAproject.service.CompanyAdministratorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/administrator")
public class AdministratorController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private AdministratorService administratorService;

	@Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody AdministratorDto administratorDto)
	{
		administratorService.createAdministrator(administratorDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
