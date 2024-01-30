package com.example.ISAproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.dto.EquipmentDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.service.CompanyService;
import com.example.ISAproject.service.EquipmentService;
import com.example.ISAproject.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/equipment")
public class EquipmentController {
	
	private EquipmentService equipmentService;
	private CompanyService companyService;
	
	@Autowired
    public EquipmentController(EquipmentService equipmentService, CompanyService companyService) {
        this.equipmentService = equipmentService;
        this.companyService = companyService;
    }
	
	@GetMapping("/all")
	public ResponseEntity<List<EquipmentDto>> getAllEquipment() {

		List<Equipment> equipment = equipmentService.getAllEquipment();

		if ( equipment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<EquipmentDto> equipmentDtos = new ArrayList<>();
		for (Equipment e : equipment) {
			equipmentDtos.add(new EquipmentDto(e));
		}
                
        return new ResponseEntity<>(equipmentDtos, HttpStatus.OK); 
	}
	
	@PutMapping("/update")
    public ResponseEntity<EquipmentDto> updateEquipment(
            @RequestBody EquipmentDto updatedEquipmentDto
    ) {
        Equipment equipmentToUpdate = equipmentService.findEquipmentById(updatedEquipmentDto.getId());

        if (equipmentToUpdate != null) {
        	equipmentToUpdate.setName(updatedEquipmentDto.getName());
        	equipmentToUpdate.setType(updatedEquipmentDto.getType());
        	equipmentToUpdate.setDescription(updatedEquipmentDto.getDescription());
        	equipmentToUpdate.setQuantity(updatedEquipmentDto.getQuantity());
            
           

            Equipment updatedEquipment = equipmentService.updateEquipment(equipmentToUpdate);
            return new ResponseEntity<>(new EquipmentDto(updatedEquipment), HttpStatus.OK);
        } else {
        	System.out.println("Oprema nije pronađena za ažuriranje.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody EquipmentDto equipmentDto)
	{
		
		Equipment equipment = new Equipment();
		equipment.setName(equipmentDto.getName());
		equipment.setType(equipmentDto.getType());
		equipment.setDescription(equipmentDto.getDescription());
		equipment.setQuantity(equipmentDto.getQuantity());
		//dodato
		equipment.setReservedQuantity(0);
		equipment.setPrice(45);
		
		equipmentService.save(equipment);
		
		return new ResponseEntity<>(HttpStatus.CREATED);

	}
	
	@GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentDto> findEquipmentById(@PathVariable Long equipmentId) {
        Equipment equipment = equipmentService.findEquipmentById(equipmentId);

        if (equipment != null) {
            return new ResponseEntity<>(new EquipmentDto(equipment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping("/{equipmentId}")
	public ResponseEntity<String> deleteEquipment(@PathVariable Long equipmentId) {
	    Equipment equipment = equipmentService.findEquipmentById(equipmentId);

	    if (equipment != null) {
	        equipmentService.deleteEquipment(equipmentId);
	        return new ResponseEntity<>("Equipment successfully deleted", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Equipment not found", HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping(value = "/{companyId}/addToCompany")
	public ResponseEntity<String> AddEquipmentToCompany(@RequestBody EquipmentDto equipmentDto, @PathVariable Long companyId) {
	    try {
	        Equipment equipment = new Equipment();
	        equipment.setName(equipmentDto.getName());
	        equipment.setType(equipmentDto.getType());
	        equipment.setDescription(equipmentDto.getDescription());
	        equipment.setQuantity(equipmentDto.getQuantity());

	        // Čuvanje opreme
	        Equipment savedEquipment = equipmentService.save(equipment);

	        if (savedEquipment != null) {
	            // Pronalaženje kompanije
	            Company company = companyService.findCompanyById(companyId);

	            if (company != null) {
	                // Dodavanje opreme kompaniji
	                company.getEquipment().add(savedEquipment);
	                companyService.updateCompany(company);
	                return new ResponseEntity<>(HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>("Failed to save equipment", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (Exception ex) {
	        return new ResponseEntity<>("Error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/reduceQuantity/{companyId}")
    public ResponseEntity<String> reduceEquipmentQuantity(
            @PathVariable Long companyId,
            @RequestBody Reservation reservation) {
        try {
            equipmentService.reduceEquipmentQuantity(companyId, reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
