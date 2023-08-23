package com.spring.railEase.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	private LocalDate reservationDate;
	private String travelDate;
	private String source;
	private String destination;
	private String bookingStatus;
	private Integer noOfSeats;
	private String seatType;
	private Integer totalTicketPrice;
	@ManyToOne
	@JoinColumn(name="customerid")
	Customer customer;
	@ManyToOne
	@JoinColumn(name="trainno")
	Train train;
	@OneToMany(mappedBy = "reservation",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Passenger> passengerList;
	
}
