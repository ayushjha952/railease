package com.spring.railEase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.railEase.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	@Query("select sum(r.noOfSeats) from Reservation r where r.train.trainNo=?1 and r.travelDate=?2 and r.seatType=?3 and r.bookingStatus='Booked'")
	public Optional<Integer> getTotalNoOfBookings(String trainNo,String travelDate,String seatType);
	
	@Query("select r from Reservation r where r.customer.customerId=?1")
	public Optional<List<Reservation>> getAllReservationsByCustomerId(Integer customerId);
}
