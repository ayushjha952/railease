package com.spring.railEase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table
public class CancelledReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cancellationId;
	@OneToOne
	@JoinColumn(name = "reservationId")
	private Reservation reservation;
	private Integer refundAmount;
}
