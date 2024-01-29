package com.example.ISAproject.service;

import java.util.ArrayList;
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
	
	public List<Complaint> getAllNotRespondedComplaints() {
	    List<Complaint> allComplaints = complaintRepository.findAll();
	    List<Complaint> notRespondedComplaints = new ArrayList<>();

	    for (Complaint complaint : allComplaints) {
	        if (!complaint.getResponded()) {
	            notRespondedComplaints.add(complaint);
	        }
	    }

	    return notRespondedComplaints;
	}
}
