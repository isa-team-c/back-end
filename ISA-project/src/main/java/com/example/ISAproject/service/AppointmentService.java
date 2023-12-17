package com.example.ISAproject.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.Role;
import com.example.ISAproject.model.User;
import com.example.ISAproject.repository.AppointmentRepository;

@Service
public class AppointmentService {

	
	@Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
	
	public List<Appointment> generateAppointments(LocalDateTime selectedDateTime, int duration, long companyId) {
		Company company = companyService.findById(companyId);
        LocalTime workStartTime = company.getWorkStartTime();
        LocalTime workEndTime = company.getWorkEndTime();
        
        LocalDateTime endDateTime = selectedDateTime.plusMinutes(duration);
        

        List<Appointment> generatedAppointments = new ArrayList<>();
        
        while (endDateTime.toLocalTime().isBefore(workEndTime)) {
            if (isAdministratorAvailable(selectedDateTime, duration, companyId) != null) {
            	CompanyAdministrator administartor = isAdministratorAvailable(selectedDateTime, duration, companyId);
            	if (selectedDateTime.toLocalTime().isBefore(workStartTime) || endDateTime.toLocalTime().isAfter(workEndTime)) {
                    throw new IllegalArgumentException("Termin nije unutar radnog vremena firme.");
                }
                Appointment appointment = new Appointment();
                appointment.setStartDate(selectedDateTime);
                appointment.setDuration(duration);
                appointment.setIsFree(true);
                appointment.setCompanyAdministrator(administartor);

                generatedAppointments.add(appointment);
            }

            selectedDateTime = selectedDateTime.plusMinutes(duration);
            endDateTime = endDateTime.plusMinutes(duration);
        }
        
        return generatedAppointments;
	}
	
	public CompanyAdministrator isAdministratorAvailable(LocalDateTime start, int duration, long companyId) {
	    LocalDateTime end = start.plusMinutes(duration);

	    Company company = companyService.findById(companyId);

	    
	    List<CompanyAdministrator> administartors = companyAdministratorService.findByCompany(company);
	    for (CompanyAdministrator companyAdministrator : administartors) {
	        boolean isAvailable = true;

	        List<Appointment> appointments = findByCompanyAdministrator(companyAdministrator);
	        for (Appointment appointment : appointments) {
	            LocalDateTime appointmentStart = appointment.getStartDate();
	            LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointment.getDuration());

	            if (end.isAfter(appointmentStart) && start.isBefore(appointmentEnd)) {
	                isAvailable = false;
	                break;  // Ne treba dalje proveravati, jer je već utvrđeno da administrator nije dostupan
	            }
	        }

	        if (isAvailable) {
	            return companyAdministrator;
	        }
	    }

	    return null;
	    
	}
	
	public List<Appointment> findByCompanyAdministrator(CompanyAdministrator administrator) {
        return appointmentRepository.findByCompanyAdministrator(administrator);
    }
	
	public Appointment saveGeneratedAppointment(AppointmentDto appointmentDto) {
	    try {
	        Appointment appointment = new Appointment();

	        CompanyAdministratorDto administratorDto = appointmentDto.getCompanyAdministrator();
	        long administratorId = administratorDto.getId();
	        CompanyAdministrator administrator = companyAdministratorService.findById(administratorId);
	        appointment.setCompanyAdministrator(administrator);

	        appointment.setStartDate(appointmentDto.getStartDate());
	        appointment.setDuration(appointmentDto.getDuration());
	        appointment.setIsFree(false);

	        appointmentRepository.save(appointment);

	        Company company = administrator.getCompany();
	        company.getAppointments().add(appointment);
	        companyService.update(company);

	        return appointment;
	    } catch (Exception e) {
	        throw new RuntimeException("An error occurred while saving the appointment.", e);
	    }
	}

	
	

}
