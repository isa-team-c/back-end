package com.example.ISAproject.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	private CompanyAdministratorRepository companyAdministratorRepository;
	
	@Autowired
    public UserService(UserRepository userRepository, CompanyAdministratorRepository companyAdministratorRepository) {
        this.userRepository = userRepository;
        this.companyAdministratorRepository = companyAdministratorRepository;
    }

	public User findOne(Long id) {
		return userRepository.findById(id).orElseGet(null);
	}
	
	 public List<User> getCompanyAdmins() {
	        return userRepository.findByRole(UserRole.ROLE_COMPANY_ADMIN);
	    }

	 public User update(UserDto updatedUser) {
	        User existingUser = userRepository.findById(updatedUser.getId()).orElseGet(null);

	        if (existingUser != null) {
	            existingUser.setName(updatedUser.getName());
	            existingUser.setSurname(updatedUser.getSurname());
	            existingUser.setCity(updatedUser.getCity());
	            existingUser.setCountry(updatedUser.getCountry());
	            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
	            existingUser.setProfession(updatedUser.getProfession());
	            existingUser.setCompanyInformation(updatedUser.getCompanyInformation());

	            return userRepository.save(existingUser);
	        } else {

	            throw new EntityNotFoundException("User not found with ID: " + updatedUser.getId());
	        }
	    }
	 
	 

	   /* public Company getCompanyForUserId(long userId) {
	    	try {
		        User user = userRepository.findById(userId).orElse(null);
	
		        if (user != null) {
		            CompanyAdministrator companyAdministrator = companyAdministratorRepository.findByUser(user);
	
		            if (companyAdministrator != null) {
		                // Prikazivanje samo prve pronađene kompanije za korisnika
		                return companyAdministrator.getCompany();
		            }
		        }
	        
	    } catch (Exception e) {
	        e.printStackTrace(); // Ispisuje iznimku u konzoli
	    }

	        return null; // Return null if user is not associated with any company administrator or if company is not found
	    }*/
}
