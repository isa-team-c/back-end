package com.example.ISAproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.UserRole;
import com.example.ISAproject.service.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController{
	
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
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto)
	{
		/*if (userDto.getPassword() != userDto.getConfirmationPassword())
		{
			return new ResponseEntity<>(HttpStatus.);
		}*/
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
		
		userService.save(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	

}
