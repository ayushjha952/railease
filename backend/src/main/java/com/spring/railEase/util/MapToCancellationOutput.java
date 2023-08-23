package com.spring.railEase.util;

import java.time.LocalDate;

import com.spring.railEase.entity.CancelledReservation;
import com.spring.railEase.model.CancellationOutputModel;

public class MapToCancellationOutput {
	public CancellationOutputModel mapToCancellation(CancelledReservation cancelledReservation) {
		CancellationOutputModel cancellationOutputModel = new CancellationOutputModel();
		cancellationOutputModel.setReservationOutputModel(new MapToReservationOutput().mapToReservation(cancelledReservation.getReservation()));
		LocalDate currDate=LocalDate.now();
		LocalDate travelDate = LocalDate.parse(cancellationOutputModel.getReservationOutputModel().getTravelDate());
		if(currDate.isAfter(travelDate) || currDate.isEqual(travelDate))
		cancellationOutputModel.setCancelledPossible(false);
		else
		cancellationOutputModel.setCancelledPossible(true);
		cancellationOutputModel.setRefundAmount(cancelledReservation.getRefundAmount());
		cancellationOutputModel.setCancellationId(cancelledReservation.getCancellationId());
		return cancellationOutputModel;
	}
}
