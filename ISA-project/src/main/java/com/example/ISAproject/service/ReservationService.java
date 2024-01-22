package com.example.ISAproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.repository.ReservationRepository;
import com.example.ISAproject.repository.UserRepository;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.EquipmentRepository;

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
	
	
	public Reservation reserveEquipment(List<Long> equipmentIds, Long appointmentId, Long userId) {        
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

        if (!appointment.getIsFree()) {
        	
        }
        
        Reservation reservation = new Reservation();
        
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setEquipment(new HashSet<>(equipmentList));
        reservation.setAppointment(appointment);
        appointment.setIsFree(false);
        reservation.setUser(user);
        
        appointmentRepository.save(appointment);
        
        return reservationRepository.save(reservation);
	}
	
	//future appointments
	public List<AppointmentDto> getAppointmentsByUserId(long userId) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		
        List<Reservation> userReservations = reservationRepository.getByUserId(userId);
        
        List<Reservation> activeAndFutureReservations = userReservations.stream()
                .filter(reservation -> !reservation.getStatus().equals(ReservationStatus.CANCELLED))
                .filter(reservation -> reservation.getAppointment().getStartDate().isAfter(currentDateTime))
                .collect(Collectors.toList());
        
        List<AppointmentDto> userAppointments = new ArrayList<>();

        for (Reservation reservation : activeAndFutureReservations) {
            userAppointments.add(new AppointmentDto(reservation.getAppointment()));
        }

        return userAppointments;
    }
	
}
