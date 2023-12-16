package com.example.ISAproject.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.enumerations.UserRole;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.CompanyRepository;
import com.example.ISAproject.repository.EquipmentRepository;
import com.example.ISAproject.repository.UserRepository;


import java.util.Optional;
import java.util.Set;

import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
	private CompanyAdministratorRepository companyAdministratorRepository;
	private EquipmentRepository equipmentRepository;

	 @Autowired
        
    public CompanyService(CompanyAdministratorRepository companyAdministratorRepository,EquipmentRepository equipmentRepository ) {
       this.companyAdministratorRepository = companyAdministratorRepository;
       this.equipmentRepository = equipmentRepository;
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
	
	public Set<Equipment> getEquipmentByCompanyId(long companyId) {
        Company company = findCompanyById(companyId);

        if (company == null) {
            throw new EntityNotFoundException("Company not found with id: " + companyId);
        }

        return company.getEquipment();
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
    
    @Transactional
    public void deleteEquipmentByCompanyIdAndEquipmentId(long companyId, long equipmentId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));

        Equipment equipmentToDelete = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + equipmentId));

        // Provera da li je oprema zaista povezana sa datom kompanijom
        if (!company.getEquipment().contains(equipmentToDelete)) {
            throw new EntityNotFoundException("Equipment with id " + equipmentId + " is not associated with company id " + companyId);
        }

        // Uklanjanje opreme iz liste opreme kompanije
        company.getEquipment().remove(equipmentToDelete);

        // Brisanje opreme iz baze podataka
        equipmentRepository.delete(equipmentToDelete);
    }


}
