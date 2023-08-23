package com.spring.railEase.model;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancellationOutputModel {
	private ReservationOutputModel reservationOutputModel;
	private Integer cancellationId;
	private Integer refundAmount;
	private boolean cancelledPossible;
}
