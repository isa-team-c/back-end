package com.example.ISAproject.controller;

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
	private CompanyAdministratorService companyAdministratorService;
	
	public AppointmentController(CompanyAdministratorService companyAdministratorService) {
        this.companyAdministratorService = companyAdministratorService;
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
	
	@PostMapping(value = "/generated")
    public ResponseEntity<AppointmentDto> saveGeneratedAppointment(@RequestBody AppointmentDto appointmentDto)
    {

        Appointment appointment = appointmentService.saveGeneratedAppointment(appointmentDto);

        return new ResponseEntity<AppointmentDto>(new AppointmentDto(appointment), HttpStatus.CREATED);
    }

}
