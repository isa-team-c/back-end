package com.example.ISAproject.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PostMapping(value = "/auth/register")  //dozvoljeno svima
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto)
	{
		if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
			return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.BAD_REQUEST);
		}
		
	    if (userService.findByEmail(userDto.getEmail()) != null) {
	        return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
	    }
				
	    User savedUser = userService.save(userDto);
		
		try {
			System.out.println("Thread id: " + Thread.currentThread().getId());
			emailService.sendNotificaitionAsync(savedUser);			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
				
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/verify/{id}")
	public ResponseEntity<String> verifyUser(@PathVariable  long id) {			
	    userService.verifyUser(id);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/whoami")
	public User user(Principal user) {
		return this.userService.findByEmail(user.getName());
	}
	
	@GetMapping("/getByEmail/{email}")
	public User user(@PathVariable String email) {
		return this.userService.findByEmail(email);
	}
	
	@GetMapping("/foo")
    public Map<String, String> getFoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("foo", "bar");
        return fooObj;
    }
}
