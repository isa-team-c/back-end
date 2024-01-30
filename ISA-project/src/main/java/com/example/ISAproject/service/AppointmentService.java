package com.example.ISAproject.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.Role;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.EquipmentRepository;
import com.example.ISAproject.repository.RegularUserRepository;
import com.example.ISAproject.repository.ReservationRepository;
import com.example.ISAproject.repository.UserRepository;

@Service
public class AppointmentService {

	
	@Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RegularUserRepository regularUserRepository;
    
    @Autowired 
    private ReservationRepository reservationRepository;
    
    @Autowired
    private EquipmentRepository equipmentRepository;
	
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
	        companyService.update(company);

	        return appointment;
	    } catch (Exception e) {
	        throw new RuntimeException("An error occurred while saving the appointment.", e);
	    }


	}
	
	public void cancelAppointment(long appointmentId, long userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        RegularUser regularUser = regularUserRepository.findByUser(user);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime appointmentStart = appointment.getStartDate();
        

        Duration timeDifference = Duration.between(currentDateTime, appointmentStart);

        int penaltyReduction = (timeDifference.toDays() <= 1) ? 2 : 1;
        updatePenalties(regularUser, penaltyReduction);
        
        Reservation reservation = reservationRepository.findByAppointmentAndUser(appointment, user);
        updateReservationAndEquipment(reservation, ReservationStatus.CANCELLED);
      
        updateAppointmentStatus(appointment, true);
	}

	public Appointment saveAppointment(AppointmentDto appointmentDto) {
	    try {
	        Appointment appointment = new Appointment();

	        CompanyAdministratorDto administratorDto = appointmentDto.getCompanyAdministrator();
	        long administratorId = administratorDto.getId();
	        CompanyAdministrator administrator = companyAdministratorService.findById(administratorId);
	        appointment.setCompanyAdministrator(administrator);

	        appointment.setStartDate(appointmentDto.getStartDate());
	        appointment.setDuration(appointmentDto.getDuration());
	        appointment.setIsFree(true);

	        appointmentRepository.save(appointment);

	        Company company = administrator.getCompany();
	        company.getAppointments().add(appointment);
	        companyService.update(company);

	        return appointment;
	    } catch (Exception e) {
	        throw new RuntimeException("An error occurred while saving the appointment.", e);
	    }

	}

	private void updatePenalties(RegularUser regularUser, int penaltyReduction) {
	    regularUser.setPenalties(regularUser.getPenalties() + penaltyReduction);
	    regularUserRepository.save(regularUser);
	}
	
	private void updateReservationAndEquipment(Reservation reservation, ReservationStatus status) {
	    reservation.setStatus(status);
	    reservationRepository.save(reservation);
	    
        for (Equipment equipment : reservation.getEquipment())
        {
        	equipment.setReservedQuantity(equipment.getReservedQuantity() - 1);
        	equipmentRepository.save(equipment);
        }
	}

	private void updateAppointmentStatus(Appointment appointment, boolean isFree) {
	    appointment.setIsFree(isFree);
	    appointmentRepository.save(appointment);
	}
	
	
	public long getAppointmentCountByYear(long companyId, int year) {
	    Company company = companyService.findById(companyId);

	    LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
	    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);

	    List<Appointment> appointments = company.getAppointments().stream()
	            .filter(appointment -> appointment.getStartDate().isAfter(startOfYear) && appointment.getStartDate().isBefore(endOfYear))
	            .collect(Collectors.toList());

	    return appointments.size();
	}

	public List<Long> getAppointmentCountsByQuarter(long companyId, int year) {
	    Company company = companyService.findById(companyId);
	    List<Long> appointmentCounts = new ArrayList<>();

	    for (int i = 1; i <= 4; i++) {
	        LocalDateTime startOfQuarter = LocalDateTime.of(year, (i - 1) * 3 + 1, 1, 0, 0);
	        LocalDateTime endOfQuarter = startOfQuarter.plusMonths(3).minusSeconds(1);

	        List<Appointment> appointments = company.getAppointments().stream()
	                .filter(appointment -> appointment.getStartDate().isAfter(startOfQuarter) && appointment.getStartDate().isBefore(endOfQuarter))
	                .collect(Collectors.toList());

	        appointmentCounts.add((long) appointments.size());
	    }

	    return appointmentCounts;
	}
	
	
	

	public List<Long> getAppointmentCountsByMonth(long companyId, int year, int month) {
	    Company company = companyService.findById(companyId);
	    LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
	    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

	    List<Appointment> appointments = company.getAppointments().stream()
	            .filter(appointment -> appointment.getStartDate().isAfter(startOfMonth) && appointment.getStartDate().isBefore(endOfMonth))
	            .collect(Collectors.toList());

	    return Collections.singletonList((long) appointments.size());
	}


	  
}
