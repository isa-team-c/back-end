package com.example.ISAproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;
import com.example.ISAproject.service.EmailService;
import com.example.ISAproject.service.UserService;




@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController{
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private EmailService emailService;
	
	private UserService userService;

	@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {

		User user = userService.findOne(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UserDto>(new UserDto(user), HttpStatus.OK);
	}
	
	
	/*@GetMapping("/{userId}")
    public ResponseEntity<Company> getCompanyForUser(@PathVariable("userId") long userId) {
        Company company = userService.getCompanyForUserId(userId);

        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
	@PostMapping(value = "/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto)
	{
		if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
			return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.BAD_REQUEST);
		}
				
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setCity(userDto.getCity());
		user.setCountry(userDto.getCountry());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setProfession(userDto.getProfession());
		user.setCompanyInformation(userDto.getCompanyInformation());
		user.setRole(UserRole.ROLE_REGULAR);
		user.setIsVerified(false);
		
		try {
			System.out.println("Thread id: " + Thread.currentThread().getId());
			emailService.sendNotificaitionAsync(user);			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		userService.save(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/verify/{id}")
	public ResponseEntity<String> verifyUser(@PathVariable  long id) {
	    User user = userService.findOne(id);
	    if (user == null) {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }
	    user.setIsVerified(true);
	    userService.save(user);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
}
