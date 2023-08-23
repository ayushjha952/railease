package com.spring.railEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Passenger;
import com.spring.railEase.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService{
	@Autowired
	PassengerRepository passengerRepository;
	
	public Passenger addPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	public void removePassengerByReservationId(Integer reservationId) {
		passengerRepository.removePassengerByReservationId(reservationId);
		
	}
}
