package com.example.ISAproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.enumerations.ReservationStatus;
import com.example.ISAproject.model.Company;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.User;
import com.example.ISAproject.model.Appointment;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	 List<Reservation> findByEquipmentId(long equipmentId);
	 Reservation getById(long id);
	 List<Reservation> getByUserId(long userId);
	 List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status);
	 Reservation findByAppointmentAndUser(Appointment appointment, User user);
	 List<Reservation> findAll();
	 List<Reservation> findByStatus(ReservationStatus status);
	 List<Reservation> findByStatusNot(ReservationStatus status);
	 
	 @Modifying
	    @Query("UPDATE Reservation r SET r.status = :status WHERE r.id = :reservationId")
	    int updateStatus(@Param("reservationId") Long reservationId, @Param("status") ReservationStatus status);
	 
	 @Query("SELECT r FROM Reservation r " +
	            "JOIN FETCH r.appointment a " +
	            "JOIN FETCH a.companyAdministrator ca " +
	            "JOIN FETCH ca.user " +
	            "WHERE ca.company.id = :companyId AND r.status = 3")
	    List<Reservation> findTakenReservationsByCompanyId(Long companyId);
	
}
