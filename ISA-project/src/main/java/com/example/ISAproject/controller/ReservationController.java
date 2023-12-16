package com.example.ISAproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.service.EmailService;
import com.example.ISAproject.service.ReservationService;
import com.example.ISAproject.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reservation")
public class ReservationController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ReservationService reservationService;
	
	@PostMapping("reserveEquipment/{equipmentId}/{appointmentId}/{userId}")
    public ResponseEntity<String> reserveEquipment(@PathVariable Long equipmentId, @PathVariable Long appointmentId,
            @PathVariable Long userId) {
		
		Reservation savedReservation = reservationService.reserveEquipment(equipmentId, appointmentId, userId);
		
		try {
			System.out.println("Thread id: " + Thread.currentThread().getId());
			emailService.sendReservationConfirmationEmail(savedReservation);			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
