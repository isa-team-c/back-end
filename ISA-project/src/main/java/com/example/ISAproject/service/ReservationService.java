package com.example.ISAproject.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.AppointmentDto;
import com.example.ISAproject.dto.ReservationDto;
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
	
}
