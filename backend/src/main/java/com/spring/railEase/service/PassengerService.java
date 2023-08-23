package com.spring.railEase.service;

import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Passenger;

@Service
public interface PassengerService {
	public Passenger addPassenger(Passenger passenger);

	public void removePassengerByReservationId(Integer reservationId);
}
