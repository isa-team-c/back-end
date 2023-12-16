package com.example.ISAproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.User;

public interface CompanyAdministratorRepository extends JpaRepository<CompanyAdministrator, Long>{
	 Company findCompanyByUserId(Long userId);
	 CompanyAdministrator findByUser(User user);
	 CompanyAdministrator findByUserId(Long userId);
}
