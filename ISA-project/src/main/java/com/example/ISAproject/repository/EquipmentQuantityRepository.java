package com.example.ISAproject.repository;

import java.util.List;

import com.example.ISAproject.model.EquipmentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentQuantityRepository extends JpaRepository<EquipmentQuantity, Long>{
	List<EquipmentQuantity> findByReservation_id(long reservationId);
}
