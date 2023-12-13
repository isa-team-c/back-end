package com.example.ISAproject.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.CompanyRepository;
import com.example.ISAproject.repository.UserRepository;


import java.util.Optional;
import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;

	 @Autowired
        
    public CompanyService(CompanyAdministratorRepository companyAdministratorRepository) {
       this.companyAdministratorRepository = companyAdministratorRepository;
    }

	public List<Company> searchCompanies(String searchTerm) {
	    if (searchTerm != null) {
	        return companyRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(searchTerm, searchTerm);
	    } else {
	        return companyRepository.findAll();
	    }
	}
	
	public Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }
	
	public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}

	public Company updateCompany(Company updatedCompany) {
                
        if (companyRepository.existsById(updatedCompany.getId())) {
                        
            return companyRepository.save(updatedCompany);
        } else {
                
            return null;
    	}
    }
        
        /*public Company getCompanyByAdministratorId(Long administratorId) {
        return companyAdministratorRepository.findCompanyByUserId(administratorId);
    }*/
        
    public Company findCompanyByAdministratorId(Long administratorId) {
    	Optional<CompanyAdministrator> companyAdministratorOptional = companyAdministratorRepository.findById(administratorId);
			if (companyAdministratorOptional.isPresent()) {
				CompanyAdministrator companyAdministrator = companyAdministratorOptional.get();
				return companyAdministrator.getCompany();
			}
			return null; // ili obradite situaciju na drugi način prema vašim potrebama
    }
}
