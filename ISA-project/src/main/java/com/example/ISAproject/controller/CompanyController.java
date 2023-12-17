package com.example.ISAproject.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.service.CompanyService;
import com.example.ISAproject.model.User;
import com.example.ISAproject.service.UserService;


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
	                company.getAverageRating(),
	                company.getWorkStartTime(),
	                company.getWorkEndTime()
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
	                company.getAverageRating(),
	                company.getWorkStartTime(),
	                company.getWorkEndTime()
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
		company.setWorkStartTime(null);
		company.setWorkEndTime(null);
		companyService.save(company);
		
		return new ResponseEntity<>(HttpStatus.CREATED);

	}


	
	
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

	
	/*@PostMapping
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
	}*/
	
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
            companyToUpdate.setWorkStartTime(updatedCompanyDto.getWorkStartTime());
            companyToUpdate.setWorkEndTime(updatedCompanyDto.getWorkEndTime());
           

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

	@DeleteMapping("/{companyId}/equipment/{equipmentId}")
    public ResponseEntity<String> deleteCompanyEquipment(
            @PathVariable long companyId,
            @PathVariable long equipmentId
    ) {
        try {
            companyService.deleteEquipmentByCompanyIdAndEquipmentId(companyId, equipmentId);
            return new ResponseEntity<>("Equipment deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/save-with-equipment")
    public ResponseEntity<Company> saveCompanyWithEquipment(
            @RequestBody Company company,
            @RequestBody List<Equipment> equipmentList) {

        Company savedCompany = companyService.saveCompanyWithEquipment(company, equipmentList);
        return ResponseEntity.ok(savedCompany);
    }

	@PostMapping("/{companyId}/add-equipment")
    public ResponseEntity<Company> addEquipmentToCompany(
            @PathVariable("companyId") Long companyId,
            @RequestBody Equipment equipmentToAdd
    ) {
        try {
            Company company = companyService.findCompanyById(companyId);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            
            Company updatedCompany = companyService.addEquipmentToCompany(company, equipmentToAdd);
            return ResponseEntity.ok(updatedCompany);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
}
