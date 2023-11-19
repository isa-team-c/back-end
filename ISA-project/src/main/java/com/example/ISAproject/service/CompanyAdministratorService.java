package com.example.ISAproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.repository.CompanyAdministratorRepository;

@Service
public class CompanyAdministratorService {

    @Autowired
    private CompanyAdministratorRepository companyAdministratorRepository;

    @Autowired
    private UserService userService;

    public CompanyAdministrator findById(Long id) {
        return companyAdministratorRepository.findById(id).orElseGet(null);
    }


    public CompanyAdministrator updateCompanyAdministrator(CompanyAdministratorDto updatedUser) {
        CompanyAdministrator existingUser = companyAdministratorRepository.findById(updatedUser.getId()).orElseGet(null);
        UserDto user = updatedUser.getUser();
        userService.update(user);
        return companyAdministratorRepository.save(existingUser);

    }
}