package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Appointment;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.CompanyAdministrator;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	Appointment getById(long id); 
	
	
	List<Appointment> findByCompanyAdministrator(CompanyAdministrator administrator);
}
