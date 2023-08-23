package com.spring.railEase.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.railEase.entity.Passenger;
import com.spring.railEase.entity.Reservation;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.model.PassengerOutputModel;

public class MapToReservationOutput {
	public ReservationOutputModel mapToReservation(Reservation reservation) {
		ReservationOutputModel reservationOutputModel=new ReservationOutputModel();
		reservationOutputModel.setBookingStatus(reservation.getBookingStatus());
		reservationOutputModel.setCustomerId(reservation.getCustomer().getCustomerId());
		reservationOutputModel.setDestination(reservation.getDestination());
		reservationOutputModel.setNoOfSeats(reservation.getNoOfSeats());
		reservationOutputModel.setReservationDate(reservation.getReservationDate());
		reservationOutputModel.setReservationId(reservation.getReservationId());
		reservationOutputModel.setSeatType(reservation.getSeatType());
		reservationOutputModel.setSource(reservation.getSource());
		reservationOutputModel.setTotalTicketPrice(reservation.getTotalTicketPrice());
		reservationOutputModel.setTrainNo(reservation.getTrain().getTrainNo());
		reservationOutputModel.setTravelDate(reservation.getTravelDate());
		List<PassengerOutputModel> passengerOutputModelList = new ArrayList<>();
		for(Passenger passenger :reservation.getPassengerList())
		{
			passengerOutputModelList.add(new MapToPassengerOutput().mapToPassenger(passenger));
		}
		reservationOutputModel.setPassengerList(passengerOutputModelList);
		return reservationOutputModel;
	}
}
