package com.example.ISAproject.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;

	public List<Company> searchCompanies(String searchTerm) {
	    if (searchTerm != null) {
	        return companyRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(searchTerm, searchTerm);
	    } else {
	        return companyRepository.findAll();
	    }
	}
	
	
	public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}


	public Company findById(Long id) {
		return companyRepository.findById(id).orElseGet(null);
	}
	
	public Set<Equipment> getEquipmentByCompanyId(long companyId) {
        Company company = findById(companyId);

        if (company == null) {
            throw new EntityNotFoundException("Company not found with id: " + companyId);
        }

        return company.getEquipment();
    }
	
	public Set<Appointment> getAppointmentsByCompanyId(long companyId) {
        Company company = findById(companyId);

        if (company == null) {
            throw new EntityNotFoundException("Company not found with id: " + companyId);
        }

        return company.getAppointments();
    }
}
