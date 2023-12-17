package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.CompanyAdministratorRepository;
import com.example.ISAproject.repository.EquipmentRepository;
import com.example.ISAproject.repository.ReservationRepository;

@Service
public class AppointmentService {
	@Autowired
    private AppointmentRepository appointmentRepository;
	private CompanyAdministratorService companyAdministratorService;
	private CompanyService companyService;
	
	
	@Autowired
    
    public AppointmentService(CompanyAdministratorService companyAdministratorService, CompanyService companyService ) {
       this.companyAdministratorService = companyAdministratorService;
       this.companyService = companyService;

    }
	
	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
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
            companyService.updateCompany(company);

            return appointment;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the appointment.", e);
        }
    }
}
