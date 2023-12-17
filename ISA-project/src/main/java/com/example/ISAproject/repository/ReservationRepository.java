package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISAproject.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	Reservation getById(long id);
	List<Reservation> getByUserId(long userId);
}