package com.example.ISAproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISAproject.model.Appointment;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.repository.AppointmentRepository;
import com.example.ISAproject.repository.EquipmentRepository;

@Service
public class AppointmentService {
	@Autowired
    private AppointmentRepository appointmentRepository;
	
	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
}
