package com.example.ISAproject.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.repository.EquipmentRepository;

@Service
public class EquipmentService {
	@Autowired
    private EquipmentRepository equipmnetRepository;
	
	@Autowired
    private CompanyService companyService;
	
	public List<Equipment> getAllEquipment() {
        return equipmnetRepository.findAll();
    }
	
	public Equipment findEquipmentById(Long equipmentId) {
        return equipmnetRepository.findById(equipmentId).orElse(null);
    }
	
	public Equipment updateEquipment(Equipment updatedEquipment) {
        
        if (equipmnetRepository.existsById(updatedEquipment.getId())) {
                        
            return equipmnetRepository.save(updatedEquipment);
        } else {
                
            return null;
    	}
    }
	
	public Equipment save(Equipment equipment) {
		return equipmnetRepository.save(equipment);
	}
	
	public void deleteEquipment(Long equipmentId) {
		equipmnetRepository.deleteById(equipmentId);
    }
	
	
	public void reduceEquipmentQuantity(Long companyId, Reservation reservation) {
	    Company company = companyService.findById(companyId);

	    if (company == null) {
	        throw new EntityNotFoundException("Company not found");
	    }

	   
	        Set<Equipment> equipmentList = reservation.getEquipment();
	        for (Equipment equipment : equipmentList) {
	            Equipment existingEquipment = findEquipmentById(equipment.getId());

	            if (existingEquipment == null) {
	                throw new EntityNotFoundException("Equipment not found");
	            }

	           
	             existingEquipment.setQuantity(existingEquipment.getQuantity() - 1);
	             equipmnetRepository.save(existingEquipment);
	            
	        }
	    

	    companyService.save(company);
	}
}
