package com.example.ISAproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.User;
import com.example.ISAproject.service.CompanyService;
import com.example.ISAproject.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompanyProfile(@PathVariable Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        if (company != null) {
            CompanyDto companyDto = new CompanyDto(company);
            return new ResponseEntity<>(companyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	
	@PostMapping
	public ResponseEntity<CompanyDto> saveCompany(@RequestBody CompanyDto companyDTO) {

		//logger.info("Received company data: {}", companyDTO); //dodato
	
		System.out.println(companyDTO.getName());
		System.out.println(companyDTO.getAddress());
		System.out.println(companyDTO.getDescription());
		System.out.println(companyDTO.getAverageRating());
		
		Company company = new Company();
		company.setName(companyDTO.getName());
		company.setAddress(companyDTO.getAddress());
		company.setDescription(companyDTO.getDescription());
		company.setAverageRating(companyDTO.getAverageRating());

		company = companyService.save(company);
		return new ResponseEntity<>(new CompanyDto(company), HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
    public ResponseEntity<CompanyDto> updateCompanyProfile(
            @RequestBody CompanyDto updatedCompanyDto
    ) {
        Company companyToUpdate = companyService.findCompanyById(updatedCompanyDto.getId());

        if (companyToUpdate != null) {
            companyToUpdate.setName(updatedCompanyDto.getName());
            companyToUpdate.setAddress(updatedCompanyDto.getAddress());
            companyToUpdate.setDescription(updatedCompanyDto.getDescription());
            companyToUpdate.setAverageRating(updatedCompanyDto.getAverageRating());
           

            Company updatedCompany = companyService.updateCompany(companyToUpdate);
            return new ResponseEntity<>(new CompanyDto(updatedCompany), HttpStatus.OK);
        } else {
        	System.out.println("Kompanija nije pronađena za ažuriranje.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	
	/*@GetMapping("/company/{administratorId}")
    public ResponseEntity<Company> getCompanyByAdministratorId(@PathVariable Long administratorId) {
        Company company = companyService.getCompanyByAdministratorId(administratorId);
        
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
	
	@GetMapping("/company-for-admin/{administratorId}")
    public ResponseEntity<CompanyDto> getCompanyForAdministrator(@PathVariable Long administratorId) {
        Company company = companyService.findCompanyByAdministratorId(administratorId);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDto(company), HttpStatus.OK);
    }
}
