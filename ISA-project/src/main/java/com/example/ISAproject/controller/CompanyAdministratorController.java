package com.example.ISAproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.service.UserService;
import com.example.ISAproject.service.CompanyAdministratorService;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.service.CompanyAdministratorService;
import com.example.ISAproject.service.CompanyService;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/companyAdministrator")
public class CompanyAdministratorController{
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private CompanyAdministratorService companyAdministratorService;

	@Autowired
    public CompanyAdministratorController(CompanyAdministratorService companyAdministratorService) {
        this.companyAdministratorService = companyAdministratorService;
    }
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody CompanyAdministratorDto companyAdministratorDto)
	{
		companyAdministratorService.createCompanyAdministrator(companyAdministratorDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyAdministratorDto> get(@PathVariable Long id) {

		CompanyAdministrator user = companyAdministratorService.findById(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CompanyAdministratorDto>(new CompanyAdministratorDto(user), HttpStatus.OK);
	}
	
	@PutMapping("/update")
    public ResponseEntity<CompanyAdministratorDto> updateCompanyAdministrator(@RequestBody CompanyAdministratorDto updatedUserDto) {
		try {

			CompanyAdministrator savedCompanyAdministrator = companyAdministratorService.updateCompanyAdministrator(updatedUserDto);

            return new ResponseEntity<>(new CompanyAdministratorDto(savedCompanyAdministrator), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping(value = "/updateForPassword")
	public ResponseEntity<String> updateCompanyAdministratorForPassword(@RequestBody CompanyAdministratorDto administratorDto)
	{
		companyAdministratorService.updateCompanyAdministratorForPassword(administratorDto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
    public ResponseEntity<CompanyAdministratorDto> getCompanyAdministratorByUserId(@PathVariable Long userId) {
        CompanyAdministrator administrator = companyAdministratorService.findByUserId(userId);
        CompanyAdministratorDto administratorDto = new CompanyAdministratorDto(administrator);
        if (administrator != null) {
            return ResponseEntity.ok(administratorDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	
}
	
