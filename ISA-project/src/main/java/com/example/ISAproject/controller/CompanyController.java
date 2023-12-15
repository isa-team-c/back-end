package com.example.ISAproject.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.service.CompanyService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
    private CompanyService companyService;

	@GetMapping("/search")
	public ResponseEntity<List<CompanyDto>> searchCompanies(
	    @RequestParam(required = false) String searchTerm
	) {
	    List<CompanyDto> companyDtos;

	    if (searchTerm != null && !searchTerm.isEmpty()) {
	        List<Company> companies = companyService.searchCompanies(searchTerm);
	        companyDtos = companies.stream()
	            .map(company -> new CompanyDto(
	                company.getId(),
	                company.getName(),
	                company.getAddress(),
	                company.getDescription(),
	                company.getAverageRating()
	            ))
	            .collect(Collectors.toList());
	    } else {
	        List<Company> companies = companyService.getAllCompanies();
	        companyDtos = companies.stream()
	            .map(company -> new CompanyDto(
	                company.getId(),
	                company.getName(),
	                company.getAddress(),
	                company.getDescription(),
	                company.getAverageRating()
	            ))
	            .collect(Collectors.toList());
	    }

	    return new ResponseEntity<>(companyDtos, HttpStatus.OK);
	}

	
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody CompanyDto companyDto)
	{
		
		Company company = new Company();
		company.setName(companyDto.getName());
		company.setAddress(companyDto.getAddress());
		company.setDescription(companyDto.getDescription());
		company.setAverageRating(null);
		
		companyService.save(company);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CompanyDto> get(@PathVariable Long id) {

		Company company = companyService.findById(id);

		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CompanyDto>(new CompanyDto(company), HttpStatus.OK);
	}
	
	@GetMapping("/{companyId}/equipment")
    public ResponseEntity<Set<Equipment>> getEquipmentByCompanyId(@PathVariable long companyId) {
        try {
            Set<Equipment> equipment = companyService.getEquipmentByCompanyId(companyId);
            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{companyId}/appointments")
	public ResponseEntity<Set<AppointmentDto>> getAppointmentsByCompanyId(@PathVariable long companyId) {
	    try {
	        Set<Appointment> appointments = companyService.getAppointmentsByCompanyId(companyId);

	        Set<AppointmentDto> appointmentDtos = appointments.stream()
	                .map(appointment -> new AppointmentDto(
	                        appointment.getId(),
	                        appointment.getStartDate(),
	                        appointment.getDuration(),
	                        appointment.getIsFree()
	                ))
	                .collect(Collectors.toSet());

	        return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
	    } catch (EntityNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

}
