package com.example.ISAproject.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.repository.AppointmentRepository;

@Service
public class AppointmentService {

	
	@Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private CompanyService companyService;
    
	
	public List<Appointment> generateAppointments(LocalDateTime selectedDateTime, int duration, long companyId) {
		Company company = companyService.findById(companyId);
        LocalTime workStartTime = company.getWorkStartTime();
        LocalTime workEndTime = company.getWorkEndTime();
        
        LocalDateTime endDateTime = selectedDateTime.plusMinutes(duration);
        

        List<Appointment> generatedAppointments = new ArrayList<>();
        
        while (endDateTime.toLocalTime().isBefore(workEndTime)) {
            if (isAdministratorAvailable(selectedDateTime, duration, companyId)) {
            	if (selectedDateTime.toLocalTime().isBefore(workStartTime) || endDateTime.toLocalTime().isAfter(workEndTime)) {
                    throw new IllegalArgumentException("Termin nije unutar radnog vremena firme.");
                }
                Appointment appointment = new Appointment();
                appointment.setStartDate(selectedDateTime);
                appointment.setDuration(duration);
                appointment.setIsFree(true);
                appointment.setCompanyAdministrator(null);

                generatedAppointments.add(appointment);
            }

            selectedDateTime = selectedDateTime.plusMinutes(duration);
            endDateTime = endDateTime.plusMinutes(duration);
        }
        
        return generatedAppointments;
	}
	
	public boolean isAdministratorAvailable(LocalDateTime start, int duration, long companyId) {
	    LocalDateTime end = start.plusMinutes(duration);

	    Set<Appointment> appointments = companyService.getAppointmentsByCompanyId(companyId);
	    
	    boolean isAvailable = false;

	    for (Appointment appointment : appointments) {
	        LocalDateTime appointmentStart = appointment.getStartDate();
	        LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointment.getDuration());

	        if ((start.isBefore(appointmentEnd) || start.isEqual(appointmentEnd))
	                && (end.isAfter(appointmentStart) || end.isEqual(appointmentStart))) {
	            // Termin se preklapa, proveri naredni
	           
	        }else {
	        	// Termin se ne preklapa, postavi isAvailable na true i prekini petlju
	        	isAvailable = true;
	        	break;
	        }
	    }
	    
	    if(isAvailable) {
	    	return isAvailable;
	    }else {
	    	//Proveri da li postoji neki administrator iz te kompanije kome nije dodeljen nijedan termin
	    	List<CompanyAdministrator> administrators = companyAdministratorService.findByCompanyId(companyId);
	    	 boolean isAdministratorWithoutAppointment = administrators.stream()
	    	            .noneMatch(administrator ->
	    	                    appointments.stream()
	    	                            .anyMatch(appointment ->
	    	                                    appointment.getCompanyAdministrator().getId().equals(administrator.getId())));
	    	 
	    	 isAvailable = isAdministratorWithoutAppointment;
	    }
	    
	    return isAvailable;
	    
	}
	
	

}
