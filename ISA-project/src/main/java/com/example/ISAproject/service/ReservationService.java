package com.example.ISAproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public Reservation reserveEquipment(Long equipmentId, Long appointmentId, Long userId) {        
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        
        Reservation reservation = new Reservation();
        
        reservation.setStatus(ReservationStatus.PENDING);
        Set<Equipment> equipmentList = new HashSet<Equipment>();
        equipmentList.add(equipment);
        reservation.setEquipment(equipmentList);
        reservation.setAppointment(appointment);
        appointment.setIsFree(false);
        reservation.setUser(user);
        
        return reservationRepository.save(reservation);
	}
	
	
	
}
