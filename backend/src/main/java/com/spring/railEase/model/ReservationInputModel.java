package com.spring.railEase.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationInputModel {
	private Integer noOfSeats;
	private String travelDate;
	private String seatType;
	private String source;
	private String destination;
	private String trainNo;
	private Integer customerId;
	private List<PassengerInputModel> passengerList;
}
