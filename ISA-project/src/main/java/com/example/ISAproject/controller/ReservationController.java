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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.dto.RegularUserDto;
import com.example.ISAproject.dto.ReservationDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.User;
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
	
	@PostMapping("reserveEquipment/{equipmentIds}/{appointmentId}/{userId}")
    public ResponseEntity<String> reserveEquipment(@PathVariable List<Long> equipmentIds, @PathVariable Long appointmentId,
            @PathVariable Long userId) {
		
		Reservation savedReservation = reservationService.reserveEquipment(equipmentIds, appointmentId, userId);
		
		try {
			System.out.println("Thread id: " + Thread.currentThread().getId());
			if (savedReservation != null) {
	            emailService.sendReservationConfirmationEmail(savedReservation);
	            return new ResponseEntity<>(HttpStatus.CREATED);
	        } else {
	            logger.info("Objekat Reservation je null.");
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
    }
	
	@GetMapping("/appointmentsByUserId/{userId}")
    public ResponseEntity<List<ReservationDto>> getAppointmentsByUserId(@PathVariable Long userId) {
        List<ReservationDto> userAppointments = reservationService.getAppointmentsByUserId(userId);
        return new ResponseEntity<>(userAppointments, HttpStatus.OK);
    }
	
	@GetMapping("/takenReservationsByUserId/{userId}")
    public ResponseEntity<List<ReservationDto>> getTakenReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDto> takenReservations = reservationService.getTakenReservationsByUserId(userId);
        return new ResponseEntity<>(takenReservations, HttpStatus.OK);
    }
	
	@GetMapping("/upcomingReservationsByUserId/{userId}")
    public ResponseEntity<List<ReservationDto>> getUpcomingReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDto> upcomingReservations = reservationService.getUpcomingReservationsByUserId(userId);
        return new ResponseEntity<>(upcomingReservations, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ReservationDto> get(@PathVariable Long id) {

		Reservation reservation = reservationService.findById(id);

		if (reservation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ReservationDto>(new ReservationDto(reservation), HttpStatus.OK);
	}
	
	@PutMapping("/updateStatus")
    public ResponseEntity<ReservationDto> updateReservationStatus(
            @RequestBody ReservationDto updatedReservationDto
    ) {
        Reservation reservationToUpdate = reservationService.findById(updatedReservationDto.getId());

        if (reservationToUpdate != null) {
        	reservationToUpdate.setStatus(updatedReservationDto.getStatus());
           

            Reservation updatedReservation = reservationService.updateReservationStatus(reservationToUpdate);
            return new ResponseEntity<>(new ReservationDto(updatedReservation), HttpStatus.OK);
        } else {
        	System.out.println("Rezervacija nije pronađena za ažuriranje.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
}