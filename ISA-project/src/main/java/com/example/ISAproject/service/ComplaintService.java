package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Complaint;
import com.example.ISAproject.repository.ComplaintRepository;

@Service
public class ComplaintService {
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	
	public ComplaintService(ComplaintRepository complaintRepository) {
	       this.complaintRepository = complaintRepository;
	}
	
	public List<Complaint> getAllComplaints() {
		return complaintRepository.findAll();
	}
}
