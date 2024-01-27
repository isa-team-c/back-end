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
import com.example.ISAproject.dto.ReservationDto;
import com.example.ISAproject.exception.ReservationConflictException;
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
	
	@PostMapping("reserveEquipment/{equipmentIds}/{appointmentId}/{userId}")
    public ResponseEntity<String> reserveEquipment(@PathVariable List<Long> equipmentIds, @PathVariable Long appointmentId,
            @PathVariable Long userId) throws Exception {
		
		try {
			Reservation savedReservation = reservationService.reserveEquipment(equipmentIds, appointmentId, userId);
			
			System.out.println("Thread id: " + Thread.currentThread().getId());
			if (savedReservation != null) {
	            //emailService.sendReservationConfirmationEmail(savedReservation);
	            return new ResponseEntity<>(HttpStatus.CREATED);
	        } else {
	            logger.info("Objekat Reservation je null.");
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
		} catch (IllegalArgumentException e) {
	        // Oprema nije dostupna
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (ReservationConflictException e) {
	        // Termin nije dostupan
	        return new ResponseEntity<>("Appointment not available for reservation", HttpStatus.CONFLICT);
		}catch( Exception e ){
			logger.info(e.getMessage());
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
}