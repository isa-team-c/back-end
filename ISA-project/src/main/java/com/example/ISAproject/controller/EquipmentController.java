package com.example.ISAproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.EquipmentDto;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.service.EquipmentService;
import com.example.ISAproject.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/equipment")
public class EquipmentController {
	
	private EquipmentService equipmentService;
	
	@Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
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
}
