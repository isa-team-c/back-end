package com.example.ISAproject.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.service.AppointmentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	@Autowired
    private AppointmentService appointmentService;
	
	@GetMapping("/generate")
    public ResponseEntity<List<AppointmentDto>> generateAppointments(
    		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime selectedDateTime,
    		@RequestParam int duration,
            @RequestParam long companyId) {
		System.out.println("Received selectedDateTime: " + selectedDateTime);
		

        try {
            List<Appointment> generatedAppointments = appointmentService.generateAppointments(selectedDateTime, duration, companyId);

            List<AppointmentDto> appointmentDtos = new ArrayList<>();
            for (Appointment appointment : generatedAppointments) {
                AppointmentDto appointmentDto = new AppointmentDto();
                appointmentDto.setId(appointment.getId());
                appointmentDto.setStartDate(appointment.getStartDate());
                appointmentDto.setDuration(appointment.getDuration());
                appointmentDto.setIsFree(appointment.getIsFree());
                

                appointmentDtos.add(appointmentDto);
            }

            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
