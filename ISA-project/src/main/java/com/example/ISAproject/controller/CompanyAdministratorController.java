package com.example.ISAproject.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.service.CompanyAdministratorService;
import com.example.ISAproject.service.CompanyService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/companyAdministrator")
public class CompanyAdministratorController {
	
	private CompanyAdministratorService companyAdministratorService;
	

	@Autowired
    public CompanyAdministratorController(CompanyAdministratorService companyAdministratorService) {
        this.companyAdministratorService = companyAdministratorService;
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
	
	
}
