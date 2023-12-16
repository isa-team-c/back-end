package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>{

}
