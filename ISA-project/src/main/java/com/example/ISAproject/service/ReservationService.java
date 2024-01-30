package com.example.ISAproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.AppointmentForCompanyDto;
import com.example.ISAproject.dto.ReservationDto;
import com.example.ISAproject.dto.ReservationForCompanyDto;
import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.CompanyAdministrator;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.RegularUser;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.repository.ReservationRepository;
import com.example.ISAproject.repository.UserRepository;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.EquipmentRepository;
import com.example.ISAproject.repository.RegularUserRepository;

@Service
public class ReservationService {
	
	@Autowired
    private ReservationRepository reservationRepository;
	
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private RegularUserRepository regularUserRepository;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public Reservation reserveEquipment(List<Long> equipmentIds, Long appointmentId, Long userId) {  
		List<Reservation> reservations = reservationRepository.getByUserId(userId);
		for (Reservation reservation : reservations) {
			Appointment appointment = reservation.getAppointment();
			
			if (reservation.getStatus() == ReservationStatus.CANCELLED) {
				continue;
			}
			
			if (isOverlap(appointment, appointmentId)) {
				return null;
			}
		}
		
		List<Equipment> equipmentList = equipmentRepository.findAllById(equipmentIds);

        for (Equipment equipment : equipmentList) {
           if (equipment.getQuantity() == equipment.getReservedQuantity()) {
               return null;
           }else {
               equipment.setReservedQuantity(equipment.getReservedQuantity() + 1);
               equipmentRepository.save(equipment);
           }
        }
        
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        
        Reservation reservation = new Reservation();      
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setEquipment(new HashSet<>(equipmentList));
        for (Equipment equipment : equipmentList) {
            reservation.setPrice(reservation.getPrice() + equipment.getPrice());
        }
        reservation.setAppointment(appointment);
        appointment.setIsFree(false);
        reservation.setUser(user);
        reservation.setQrCode("");
        
        appointmentRepository.save(appointment);
        
        return reservationRepository.save(reservation);
	}
	
	 public List<ReservationDto> getAllReservations() {
		 List<Reservation> reservations = new ArrayList<>();
			List<ReservationDto> reservationsDto = new ArrayList<>();
			reservations = reservationRepository.findAll();
			for (Reservation reservation : reservations) {
	        	reservationsDto.add(new ReservationDto(reservation));
	        }
	        
	        return reservationsDto;
	  }
	 

	 
 
	 
	 
	
	 public List<Reservation> getTakenReservations() {
	        return reservationRepository.findByStatus(ReservationStatus.TAKEN);
	    }
	 
	private boolean isOverlap(Appointment existingAppointment, long newAppointmentId) {
		Appointment newAppointment = appointmentRepository.getById(newAppointmentId);
		
		if (newAppointment != null && existingAppointment != null) {
			LocalDateTime existingStart = existingAppointment.getStartDate();
		    LocalDateTime existingEnd = existingStart.plusMinutes(existingAppointment.getDuration());
		    
			LocalDateTime newStart = newAppointment.getStartDate();
		    LocalDateTime newEnd = newStart.plusMinutes(newAppointment.getDuration());
		    
		    return existingStart.isBefore(newEnd) && existingEnd.isAfter(newStart);
		}
		    
	    return false;
	}
	
	public List<ReservationDto> getAppointmentsByUserId(long userId) {
        List<Reservation> userReservations = reservationRepository.getByUserId(userId);
        List<ReservationDto> reservationsDto = new ArrayList<>();
        //List<AppointmentDto> userAppointments = new ArrayList<>();

        //for (Reservation reservation : userReservations) {
            //userAppointments.add(new AppointmentDto(reservation.getAppointment()));
        //}
        for (Reservation reservation : userReservations) {
        	reservationsDto.add(new ReservationDto(reservation));
        }

        return reservationsDto;
    }
	
	public List<ReservationDto> getTakenReservationsByUserId(long userId) {
		List<Reservation> reservations = new ArrayList<>();
		List<ReservationDto> reservationsDto = new ArrayList<>();
        reservations = reservationRepository.findByUserIdAndStatus(userId, ReservationStatus.TAKEN);
        
        for (Reservation reservation : reservations) {
        	reservationsDto.add(new ReservationDto(reservation));
        }
        
        return reservationsDto;
    }
	
	public List<Reservation> getNotTakenReservations() {
		
		
        return reservationRepository.findByStatusNot(ReservationStatus.TAKEN);
        
       
       
    }


	public boolean isAppointmentForReservation(Company company, Reservation reservation) {
	    for (Appointment appointment : company.getAppointments()) {
	        if (reservation.getAppointment().getId() == appointment.getId()) {
	            return true;
	        }
	    }
	    return false;
	}

	
	public List<ReservationDto> getUpcomingReservationsByUserId(long userId) {
		List<Reservation> reservations = new ArrayList<>();
		List<ReservationDto> reservationsDto = new ArrayList<>();
        reservations = reservationRepository.findByUserIdAndStatus(userId, ReservationStatus.PENDING);
        
        for (Reservation reservation : reservations) {
        	reservationsDto.add(new ReservationDto(reservation));
        }
        
        return reservationsDto;
    }

	public void updateQRCode(Reservation reservation) {
        // Provera da li rezervacija već postoji u bazi podataka
        if (reservationRepository.existsById(reservation.getId())) {
            reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Rezervacija sa ID-om " + reservation.getId() + " nije pronađena.");
        }
    }
	

	 public List<ReservationDto> getAllReservationsForCompany(Long companyId) {
		    Company company = companyService.findById(companyId);

		    if (company == null) {
		        throw new EntityNotFoundException("Company not found with id: " + companyId);
		    }

		    List<ReservationDto> reservationsDto = new ArrayList<>();
		    List<AppointmentForCompanyDto> appointmentsDto = new ArrayList<>();
		    
		   
		    for (Appointment appointment : company.getAppointments()) {
		        appointmentsDto.add(new AppointmentForCompanyDto(appointment));
		    }

		   
		    for (AppointmentForCompanyDto appointment : appointmentsDto) {
		    	LocalDateTime endDate = appointment.getStartDate().plusDays(appointment.getDuration());
		        for (Reservation reservation : getNotTakenReservations()) {
		            if (appointment.getId() == reservation.getAppointment().getId()) {
		               
		                if (endDate.isBefore(LocalDateTime.now())) {
		                   
		                    reservation.setStatus(ReservationStatus.REJECTED);
		                   
		                    User user = reservation.getUser();
		                    if (user != null) {
		                      RegularUser  regularUser = regularUserRepository.findByUser(user);
		                      System.out.println("RegularUser nakon poziva findByUser: " + regularUser);
		                      regularUser.setPenalties(regularUser.getPenalties()+ 2);
		                      regularUserRepository.save(regularUser);
		                    }
		                } else {
		                	if (reservation.getStatus() != ReservationStatus.REJECTED) {
		                    ReservationDto reservationDto = new ReservationDto(reservation);
		                    if (!reservationsDto.contains(reservationDto)) {
		                        reservationsDto.add(reservationDto);
		                    }
		                 }
		                }
		            }
		        }
		    }
		    return reservationsDto;
		}

	 
	 @Transactional
	 public void updateReservationStatus(Long reservationId, ReservationStatus status) {
		    Reservation Reservation = reservationRepository.getById(reservationId);
		    Appointment appointment = Reservation.getAppointment();
		    CompanyAdministrator admin = appointment.getCompanyAdministrator();
		    List<Reservation> takenReservation = reservationRepository.findTakenReservationsByCompanyId(admin.getCompany().getId());
		    for(Reservation reservation : takenReservation) {
		    	if(isOverlap(reservation.getAppointment(), appointment.getId())) {
		    		throw new IllegalStateException("Trenutno ste vec na jednom teminu preuzimanja.");
		    	}
		    	
		    }
	        reservationRepository.updateStatus(reservationId, status);
	    }
	
	 
	 public long getReservationCountByYear(long companyId, int year) {
		    LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
		    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);

		    Company company = companyService.findById(companyId);

		    if (company == null) {
		        throw new EntityNotFoundException("Company not found with id: " + companyId);
		    }

		    List<ReservationDto> reservationsDto = new ArrayList<>();

		    for (Appointment appointment : company.getAppointments()) {
		        AppointmentForCompanyDto appointmentDto = new AppointmentForCompanyDto(appointment);
		       

		        for (Reservation reservation : reservationRepository.findAll()) {
		            if (appointmentDto.getId() == reservation.getAppointment().getId() &&
		                appointment.getStartDate().isAfter(startOfYear) &&
		                appointment.getStartDate().isBefore(endOfYear)) {
		                ReservationDto reservationDto = new ReservationDto(reservation);
		                if (!reservationsDto.contains(reservationDto)) {
		                    reservationsDto.add(reservationDto);
		                }
		            }
		        }
		    }

		    long count = reservationsDto.stream().count();

		    return count;
		}
	 
	 
	 
	 public List<Long> getReservationCountsByQuarter(long companyId, int year) {
		    Company company = companyService.findById(companyId);
		    List<Long> reservationCounts = new ArrayList<>();

		    for (int i = 1; i <= 4; i++) {
		        LocalDateTime startOfQuarter = LocalDateTime.of(year, (i - 1) * 3 + 1, 1, 0, 0);
		        LocalDateTime endOfQuarter = startOfQuarter.plusMonths(3).minusSeconds(1);

		        List<ReservationDto> reservationsDto = new ArrayList<>();  // Initialize the list inside the loop

		        for (Appointment appointment : company.getAppointments()) {
		            AppointmentForCompanyDto appointmentDto = new AppointmentForCompanyDto(appointment);

		            for (Reservation reservation : reservationRepository.findAll()) {
		                if (appointmentDto.getId() == reservation.getAppointment().getId() &&
		                    appointment.getStartDate().isAfter(startOfQuarter) &&
		                    appointment.getStartDate().isBefore(endOfQuarter)) {
		                    ReservationDto reservationDto = new ReservationDto(reservation);
		                    if (!reservationsDto.contains(reservationDto)) {
		                        reservationsDto.add(reservationDto);
		                    }
		                }
		            }
		        }

		        reservationCounts.add((long) reservationsDto.size());  // Add the count to the result list
		    }

		    return reservationCounts;
		}
	 
	 
	 
	 
	 
	 
	 
	 public List<Long> getReservationCountsByMonth(long companyId, int year, int month) {
		    Company company = companyService.findById(companyId);
		    List<Long> reservationCounts = new ArrayList<>();

		    LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
		    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

		    List<ReservationDto> reservationsDto = new ArrayList<>();

		    for (Appointment appointment : company.getAppointments()) {
		        AppointmentForCompanyDto appointmentDto = new AppointmentForCompanyDto(appointment);

		        for (Reservation reservation : reservationRepository.findAll()) {
		            if (appointmentDto.getId() == reservation.getAppointment().getId() &&
		                appointment.getStartDate().isAfter(startOfMonth) &&
		                appointment.getStartDate().isBefore(endOfMonth)) {
		                ReservationDto reservationDto = new ReservationDto(reservation);
		                if (!reservationsDto.contains(reservationDto)) {
		                    reservationsDto.add(reservationDto);
		                }
		            }
		        }
		    }

		    reservationCounts.add((long) reservationsDto.size());

		    return reservationCounts;
		}


	 
	 
	 public double getReservationPriceByYear(long companyId, int year) {
		    LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
		    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);

		    Company company = companyService.findById(companyId);

		    if (company == null) {
		        throw new EntityNotFoundException("Company not found with id: " + companyId);
		    }

		    List<ReservationDto> reservationsDto = new ArrayList<>();
		    double price = 0;

		    for (Appointment appointment : company.getAppointments()) {
		        AppointmentForCompanyDto appointmentDto = new AppointmentForCompanyDto(appointment);
		       

		        for (Reservation reservation : reservationRepository.findAll()) {
		            if (appointmentDto.getId() == reservation.getAppointment().getId() &&
		                appointment.getStartDate().isAfter(startOfYear) &&
		                appointment.getStartDate().isBefore(endOfYear)) {
		                ReservationDto reservationDto = new ReservationDto(reservation);
		                if (!reservationsDto.contains(reservationDto)) {
		                    reservationsDto.add(reservationDto);
		                    price += reservationDto.getPrice();
		                }
		            }
		        }
		    }

		    
		    return price;
		}
	 

}
