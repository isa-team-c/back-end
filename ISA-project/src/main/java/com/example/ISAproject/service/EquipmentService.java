package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.repository.EquipmentRepository;

@Service
public class EquipmentService {
	@Autowired
    private EquipmentRepository equipmnetRepository;
	
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
}
