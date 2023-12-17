package com.example.ISAproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
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
	
	/*
	@PostMapping("reserveEquipment/{equipmentIds}/{appointmentId}/{userId}")
    public ResponseEntity<String> reserveEquipment(@PathVariable List<Long> equipmentIds, @PathVariable Long appointmentId,
            @PathVariable Long userId) {
		
		Reservation savedReservation = reservationService.reserveEquipment(equipmentIds, appointmentId, userId);
		
		try {
			System.out.println("Thread id: " + Thread.currentThread().getId());
			emailService.sendReservationConfirmationEmail(savedReservation);			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
    */
	
	@GetMapping("/appointmentsByUserId/{userId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByUserId(@PathVariable Long userId) {
        List<AppointmentDto> userAppointments = reservationService.getAppointmentsByUserId(userId);
        return new ResponseEntity<>(userAppointments, HttpStatus.OK);
    }

}