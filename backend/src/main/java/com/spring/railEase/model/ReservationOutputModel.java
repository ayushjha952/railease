package com.spring.railEase.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationOutputModel {
	private Integer reservationId;
	private LocalDate reservationDate;
	private String travelDate;
	private String source;
	private String destination;
	private String bookingStatus;
	private Integer noOfSeats;
	private String seatType;
	private Integer totalTicketPrice;
	private String trainNo;
	private Integer customerId;
	private List<PassengerOutputModel> passengerList;
}
