package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.CompanyAdministrator;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	Appointment getById(long id); 
	
	
	List<Appointment> findByCompanyAdministrator(CompanyAdministrator administrator);
	
	@Query("SELECT a FROM Appointment a WHERE a.companyAdministrator.company.id = :companyId")
    List<Appointment> findAllByCompanyId(@Param("companyId") Long companyId);

	List<Appointment> findByCompanyAdministratorAndStartDateBetween(CompanyAdministrator companyAdministrator, LocalDateTime startDate, LocalDateTime endDate);
}
