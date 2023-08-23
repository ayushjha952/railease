package com.spring.railEase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Reservation;
import com.spring.railEase.model.CancellationOutputModel;
import com.spring.railEase.model.ReservationInputModel;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCancellationException;
import com.spring.railEase.service.exception.InvalidReservationException;
import com.spring.railEase.service.exception.TrainNotFoundException;

import jakarta.transaction.Transactional;

@Service
public interface ReservationService {

	@Transactional
	public ReservationOutputModel makeReservation(ReservationInputModel reservationInputModel) throws InvalidReservationException, TrainNotFoundException, CustomerNotFoundException;

	@Transactional
	public CancellationOutputModel cancelReservation(Integer reservationId) throws InvalidCancellationException, InvalidReservationException;

	public List<CancellationOutputModel> getAllReservationsByCustomerId(Integer customerId) throws CustomerNotFoundException, InvalidReservationException;

	public Reservation findReservationById(Integer reservationId) throws InvalidReservationException;

	public Integer getTotalNoOfReservationsByDateAndSeatType(String trainNo, String travelDate, String seatType);
}
