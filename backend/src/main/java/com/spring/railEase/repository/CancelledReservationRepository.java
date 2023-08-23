package com.spring.railEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.railEase.entity.CancelledReservation;

public interface CancelledReservationRepository extends JpaRepository<CancelledReservation, Integer> {
	
	@Query("select c from CancelledReservation c where c.reservation.reservationId=?1")
	public CancelledReservation getCancellationDetailsByReservationId(Integer reservationId);
	
}
