package com.example.ISAproject.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import javax.persistence.EntityNotFoundException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.CompanyRepository;
import com.example.ISAproject.repository.EquipmentRepository;
import com.example.ISAproject.repository.ReservationRepository;
import com.example.ISAproject.repository.UserRepository;


import java.util.Optional;
import com.example.ISAproject.model.Equipment;
import java.util.Set;

import com.example.ISAproject.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
	private CompanyAdministratorRepository companyAdministratorRepository;
    @Autowired
	private EquipmentRepository equipmentRepository;
    @Autowired
	private ReservationRepository reservationRepository;

    @Autowired
    private CompanyAdministratorService companyAdministratorService;
 
	
	
        
    

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

	public Company findById(Long id) {
		return companyRepository.findById(id).orElseGet(null);
	}
	
	
	
	public Set<Appointment> getAppointmentsByCompanyId(long companyId) {
        Company company = findById(companyId);

        if (company == null) {
            throw new EntityNotFoundException("Company not found with id: " + companyId);
        }

        return company.getAppointments();
    }
	
	public void update(Company company) {
        companyRepository.save(company);
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

        // Provera da li je oprema rezervisana i da li su rezervacije preuzete
        if (!isEquipmentReservedAndTaken(equipmentId)) {
            // Uklanjanje opreme iz liste opreme kompanije
            company.getEquipment().remove(equipmentToDelete);

            // Brisanje opreme iz baze podataka
            equipmentRepository.delete(equipmentToDelete);
        } else {
            throw new IllegalStateException("Equipment cannot be deleted as it has pending reservations or is not taken.");
        }
    }

    private boolean isEquipmentReservedAndTaken(long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
            .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + equipmentId));

        // Dobijanje svih rezervacija za ovu opremu
        List<Reservation> reservations = reservationRepository.findByEquipmentId(equipmentId);

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() != ReservationStatus.TAKEN) {
                // Ukoliko je status rezervacije različit od TAKEN, oprema nije preuzeta
                return true;
            }
        }

        return false;
    }
    
    public Company saveCompanyWithEquipment(Company company, List<Equipment> equipmentList) {
        // Sačuvaj kompaniju
        Company savedCompany = companyRepository.save(company);

        // Postavi opremu kompaniji i sačuvaj u bazi
        savedCompany.setEquipment(new HashSet<>(equipmentList));
        companyRepository.save(savedCompany);

        return savedCompany;
    }
    
    public Company addEquipmentToCompany(Company company, Equipment equipment) {
        // Inicijalizacija liste opreme ako nije inicijalizovana
        Set<Equipment> companyEquipment = company.getEquipment();
        if (companyEquipment == null) {
            companyEquipment = new HashSet<>();
            company.setEquipment(companyEquipment);
        }

        // Dodavanje opreme kompaniji
        companyEquipment.add(equipment);

        // Sačuvajte kompaniju sa dodatom opremom
        return companyRepository.save(company);
    }



}
