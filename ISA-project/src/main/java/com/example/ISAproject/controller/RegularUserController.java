package com.example.ISAproject.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.RegularUserDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.User;
import com.example.ISAproject.service.RegularUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/regular")
public class RegularUserController {

	private RegularUserService regularUserService;

	@Autowired
    public RegularUserController(RegularUserService regularUserService) {
        this.regularUserService = regularUserService;
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<RegularUserDto> get(@PathVariable Long id) {

		RegularUser user = regularUserService.findById(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RegularUserDto>(new RegularUserDto(user), HttpStatus.OK);
	}
	
	@GetMapping("/by-user-id/{userId}")
	public ResponseEntity<RegularUserDto> getByUserId(@PathVariable Long userId) {

		RegularUser user = regularUserService.findByUserId(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RegularUserDto>(new RegularUserDto(user), HttpStatus.OK);
	}
	
	@PutMapping("/update")
    public ResponseEntity<RegularUserDto> updateRegularUser(@RequestBody RegularUserDto updatedUserDto) {
		System.out.println("Regular user u kontroleru nakon penalizacije: id: " + updatedUserDto.getId() + ", broj penala: " + updatedUserDto.getPenalties());
		try {

            RegularUser savedRegularUser = regularUserService.updateRegularUser(updatedUserDto);

            return new ResponseEntity<>(new RegularUserDto(savedRegularUser), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@Scheduled(cron = "${penalties.cron}")
	public void deletePenalties() {
		regularUserService.deletePenalties();
	}
	
	
}
