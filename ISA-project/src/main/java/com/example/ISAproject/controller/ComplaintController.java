package com.example.ISAproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.ComplaintDto;
import com.example.ISAproject.model.Complaint;
import com.example.ISAproject.service.ComplaintService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/complaint")
public class ComplaintController {
	@Autowired
	private ComplaintService complaintService;
	
	@GetMapping("/all")
	public ResponseEntity<List<ComplaintDto>> getAllComplaints() {

		List<Complaint> complaints = complaintService.getAllComplaints();

		if(complaints == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<ComplaintDto> complaintDtos = new ArrayList<>();
		for (Complaint c : complaints) {
			complaintDtos.add(new ComplaintDto(c));
		}
                
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK); 
	}
}
