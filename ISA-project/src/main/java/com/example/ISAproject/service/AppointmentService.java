package com.example.ISAproject.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.CompanyAdministratorDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.EquipmentQuantity;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.Role;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.EquipmentQuantityRepository;
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
    
	@Autowired
	private EquipmentQuantityRepository equipmentQuantityRepository;
    @Autowired
    private ReservationService reservationService;
    
    
	
    @Transactional
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

	@Transactional
	public Appointment save(Appointment appointment) {
		LocalDateTime newAppointmentEnd = appointment.getStartDate().plusMinutes(appointment.getDuration());
		if (isAppointmentOverlapForCompany(appointment.getCompanyAdministrator(), appointment.getStartDate(), newAppointmentEnd)) {
            throw new IllegalArgumentException("Termin se preklapa sa već postojećim terminom.");
        }
		return appointmentRepository.save(appointment);
	}
	
	
	@Transactional
	public Appointment saveGeneratedAppointment(AppointmentDto appointmentDto) {
	    try {
	        Appointment newAppointment = new Appointment();
	        CompanyAdministratorDto administratorDto = appointmentDto.getCompanyAdministrator();
	        long administratorId = administratorDto.getId();
	        CompanyAdministrator administrator = companyAdministratorService.findById(administratorId);
	        
	        LocalDateTime newAppointmentStart = appointmentDto.getStartDate();
	        int newAppointmentDuration = appointmentDto.getDuration();
	        LocalDateTime newAppointmentEnd = newAppointmentStart.plusMinutes(newAppointmentDuration);

	        // Provera preklapanja sa postojećim terminima
	        if (isAppointmentOverlap(administrator, newAppointmentStart, newAppointmentEnd)) {
	            throw new IllegalArgumentException("Termin se preklapa sa već postojećim terminom.");
	        }

	        newAppointment.setCompanyAdministrator(administrator);
	        newAppointment.setStartDate(newAppointmentStart);
	        newAppointment.setDuration(newAppointmentDuration);
	        newAppointment.setIsFree(true);

	        appointmentRepository.save(newAppointment);

	        Company company = administrator.getCompany();
	        company.getAppointments().add(newAppointment);
	        companyService.update(company);

	        return newAppointment;
	    } catch (Exception e) {
	        throw new RuntimeException("An error occurred while saving the appointment.", e);
	    }
	}

	private boolean isAppointmentOverlap(CompanyAdministrator administrator, LocalDateTime newStart, LocalDateTime newEnd) {
	    List<Appointment> existingAppointments = appointmentRepository.findByCompanyAdministrator(administrator);

	    for (Appointment existingAppointment : existingAppointments) {
	        LocalDateTime existingStart = existingAppointment.getStartDate();
	        LocalDateTime existingEnd = existingStart.plusMinutes(existingAppointment.getDuration());

	        if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
	            return true; // Termini se preklapaju
	        }
	    }

	    return false; // Nema preklapanja
	}
	
	private boolean isAppointmentOverlapForCompany(CompanyAdministrator administrator, LocalDateTime newStart, LocalDateTime newEnd) {
		Company company = companyService.findById(administrator.getCompany().getId());
	    List<Appointment> existingAppointments = appointmentRepository.findAllByCompanyId(company.getId());

	    for (Appointment existingAppointment : existingAppointments) {
	        LocalDateTime existingStart = existingAppointment.getStartDate();
	        LocalDateTime existingEnd = existingStart.plusMinutes(existingAppointment.getDuration());

	        if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
	            return true; // Termini se preklapaju
	        }
	    }

	    return false; // Nema preklapanja
	}

	
	@Transactional
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
        
        List<EquipmentQuantity> reservationRequests = equipmentQuantityRepository.findByReservation_id(reservation.getId());

	    for (EquipmentQuantity reservationRequest : reservationRequests) {
	        long equipmentId = reservationRequest.getEquipmentId();
	        Integer quantity = reservationRequest.getQuantity();

	        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

	        if (equipment != null) {
	            // Update the reserved quantity for each associated Equipment
	            equipment.setReservedQuantity(equipment.getReservedQuantity() - quantity);
	            equipmentRepository.save(equipment);
	        }
	    }
        
        
        updateReservationAndEquipment(reservation, ReservationStatus.CANCELLED);
      
        updateAppointmentStatus(appointment, true);
    }

	private void updatePenalties(RegularUser regularUser, int penaltyReduction) {
	    regularUser.setPenalties(regularUser.getPenalties() + penaltyReduction);
	    regularUserRepository.save(regularUser);
	}
	
	private void updateReservationAndEquipment(Reservation reservation, ReservationStatus status) {
	    reservation.setStatus(status);
	    reservationRepository.save(reservation);
	}

	private void updateAppointmentStatus(Appointment appointment, boolean isFree) {
	    appointment.setIsFree(isFree);
	    appointmentRepository.save(appointment);
	}
	
}
