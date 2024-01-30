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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.User;
import com.example.ISAproject.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.service.AppointmentService;
import com.example.ISAproject.service.CompanyAdministratorService;
import com.example.ISAproject.service.CompanyService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
	private CompanyAdministratorService companyAdministratorService;
	
	
	
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
                appointmentDto.setCompanyAdministrator(new CompanyAdministratorDto(appointment.getCompanyAdministrator()));
                

                appointmentDtos.add(appointmentDto);
            }

            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PostMapping(value = "/generated")  
	public ResponseEntity<AppointmentDto> saveGeneratedAppointment(@RequestBody AppointmentDto appointmentDto)
	{
		
	    Appointment appointment = appointmentService.saveGeneratedAppointment(appointmentDto);
				
		return new ResponseEntity<AppointmentDto>(new AppointmentDto(appointment), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/generatedAppointment")  
	public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointmentDto)
	{
		
	    Appointment appointment = appointmentService.saveAppointment(appointmentDto);
				
		return new ResponseEntity<AppointmentDto>(new AppointmentDto(appointment), HttpStatus.CREATED);
	}
	
	
	@PostMapping(value = "/create/{adminId}")
	public ResponseEntity<String> create(@PathVariable long adminId, @RequestBody AppointmentDto appointmentDto)
	{
		
		Appointment appointment = new Appointment();


		appointment.setStartDate(appointmentDto.getStartDate());
		appointment.setDuration(appointmentDto.getDuration());
		appointment.setIsFree(true);
		CompanyAdministrator companyAdministrator = companyAdministratorService.findById(adminId);
        appointment.setCompanyAdministrator(companyAdministrator);
	
		
		appointmentService.save(appointment);
		
		return new ResponseEntity<>(HttpStatus.CREATED);

	}
	
	
	@PutMapping("/cancel/{appointmentId}/{userId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable long appointmentId, @PathVariable long userId) {
        try {
            appointmentService.cancelAppointment(appointmentId, userId);
            return new ResponseEntity<>("Appointment canceled successfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
	
	
	@GetMapping("/count/year")
    public ResponseEntity<Long> getAppointmentCountByYear(@RequestParam long companyId, @RequestParam int year) {
        long count = appointmentService.getAppointmentCountByYear(companyId, year);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/quarter")
    public ResponseEntity<List<Long>> getAppointmentCountsByQuarter(@RequestParam long companyId, @RequestParam int year) {
        List<Long> counts = appointmentService.getAppointmentCountsByQuarter(companyId, year);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/count/month")
    public ResponseEntity<List<Long>> getAppointmentCountsByMonth(@RequestParam long companyId, @RequestParam int year, @RequestParam int month) {
        List<Long> counts = appointmentService.getAppointmentCountsByMonth(companyId, year, month);
        return ResponseEntity.ok(counts);
    }

  
	
	
	
	
	
	
	
	
	
}