package com.example.ISAproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AdministratorDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.RegularUserDto;
import com.example.ISAproject.model.Administrator;
import com.example.ISAproject.model.RegularUser;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<AdministratorDto> get(@PathVariable Long id) {

		Administrator administrator = administratorService.findById(id);

		if (administrator == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AdministratorDto>(new AdministratorDto(administrator), HttpStatus.OK);
	}
	
	@GetMapping("/byUserId/{userId}")
	public ResponseEntity<AdministratorDto> getByUserId(@PathVariable Long userId) {

		Administrator administrator = administratorService.findByUserId(userId);

		if (administrator == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AdministratorDto>(new AdministratorDto(administrator), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody AdministratorDto administratorDto)
	{
		administratorService.createAdministrator(administratorDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<String> update(@RequestBody AdministratorDto administratorDto)
	{
		administratorService.updateAdministrator(administratorDto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
