package com.example.ISAproject.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.ReservationDto;
import com.example.ISAproject.dto.ReservationForCompanyDto;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.service.EmailService;
import com.example.ISAproject.service.ReservationService;
import com.example.ISAproject.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
	
	@GetMapping("/allReservations")
	public ResponseEntity<List<ReservationDto>> getAllReservations() {
		List<ReservationDto> allReservations = reservationService.getAllReservations();
		return new ResponseEntity<>(allReservations, HttpStatus.OK);
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
	
	 @GetMapping("/allReservationsForCompany/{companyId}")
	    public ResponseEntity<List<ReservationDto>> getAllReservationsForCompany(@PathVariable Long companyId) {
	        try {
	            List<ReservationDto> allReservationsForCompany = reservationService.getAllReservationsForCompany(companyId);
	            return new ResponseEntity<>(allReservationsForCompany, HttpStatus.OK);
	        } catch (EntityNotFoundException e) {
	            logger.error("Error getting all reservations for company: " + e.getMessage());
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } catch (Exception e) {
	            logger.error("Error getting all reservations for company: " + e.getMessage());
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	 @PutMapping("/{reservationId}/update-status")
	    public ResponseEntity<Void> updateReservationStatus(
	            @PathVariable Long reservationId,
	            @RequestParam ReservationStatus status) {
	        reservationService.updateReservationStatus(reservationId, status);
	        return ResponseEntity.ok().build();
	    }
	
	 
	 @GetMapping("/count/year")
	    public ResponseEntity<Long> getReservationCountByYear(@RequestParam long companyId, @RequestParam int year) {
	        long count = reservationService.getReservationCountByYear(companyId, year);
	        return ResponseEntity.ok(count);
	    }

	    @GetMapping("/count/quarter")
	    public ResponseEntity<List<Long>> getReservationCountsByQuarter(@RequestParam long companyId, @RequestParam int year) {
	        List<Long> counts = reservationService.getReservationCountsByQuarter(companyId, year);
	        return ResponseEntity.ok(counts);
	    }

	    @GetMapping("/count/month")
	    public ResponseEntity<List<Long>> getReservationCountsByMonth(@RequestParam long companyId, @RequestParam int year, @RequestParam int month) {
	        List<Long> counts = reservationService.getReservationCountsByMonth(companyId, year, month);
	        return ResponseEntity.ok(counts);
	    }
	
	    @GetMapping("/price/year")
	    public ResponseEntity<Double> getReservationPriceByYear(@RequestParam long companyId, @RequestParam int year) {
	        Double price = reservationService.getReservationPriceByYear(companyId, year);
	        return ResponseEntity.ok(price);
	    }
	 
	
}