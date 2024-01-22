package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	 List<Reservation> findByEquipmentId(long equipmentId);
	 Reservation getById(long id);
	 List<Reservation> getByUserId(long userId);
	 List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status);
}
