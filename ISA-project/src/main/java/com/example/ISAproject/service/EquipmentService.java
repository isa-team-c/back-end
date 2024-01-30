package com.example.ISAproject.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.EquipmentQuantity;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.repository.EquipmentQuantityRepository;
import com.example.ISAproject.repository.EquipmentRepository;

@Service
public class EquipmentService {
	@Autowired
    private EquipmentRepository equipmnetRepository;
	
	@Autowired
    private EquipmentQuantityRepository equipmentQuantityRepository;
	
	
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

	   
	        
	        List<EquipmentQuantity> reservationRequests = equipmentQuantityRepository.findByReservation_id(reservation.getId());

		    for (EquipmentQuantity reservationRequest : reservationRequests) {
		        long equipmentId = reservationRequest.getEquipmentId();
		        Integer quantity = reservationRequest.getQuantity();

		        Equipment equipment = equipmnetRepository.findById(equipmentId).orElse(null);

		        if (equipment != null) {
		            // Update the reserved quantity for each associated Equipment
		        	equipment.setQuantity(equipment.getQuantity() - quantity);
		            equipment.setReservedQuantity(equipment.getReservedQuantity() - quantity);
		            equipmnetRepository.save(equipment);
		        }
		    }
	    

	    companyService.save(company);
	}
}
