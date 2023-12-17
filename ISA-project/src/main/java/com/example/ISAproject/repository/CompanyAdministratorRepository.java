package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;

public interface CompanyAdministratorRepository extends JpaRepository<CompanyAdministrator, Long>{
}
