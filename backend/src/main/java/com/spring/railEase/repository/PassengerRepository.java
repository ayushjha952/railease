package com.spring.railEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.railEase.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
	@Modifying
	@Query("DELETE FROM Passenger p WHERE p.reservation.reservationId=?1")
	 void removePassengerByReservationId(Integer reservationId);
}
