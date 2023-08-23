package com.spring.railEase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.railEase.model.CancellationOutputModel;
import com.spring.railEase.model.ReservationInputModel;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.service.ReservationService;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCancellationException;
import com.spring.railEase.service.exception.InvalidReservationException;
import com.spring.railEase.service.exception.TrainNotFoundException;

@RestController
@CrossOrigin("*")
public class ReservationController {

	Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	ReservationService reservationService;

	@PostMapping(value = "/addreservation")
	public ResponseEntity<ReservationOutputModel> makeReservation(
			@RequestBody ReservationInputModel reservationInputModel)
			throws InvalidReservationException, TrainNotFoundException, CustomerNotFoundException {
		logger.info("makeReservation() method started.");
		ReservationOutputModel reservationOutputModel = reservationService.makeReservation(reservationInputModel);
		logger.info("Reservation Details: {}", reservationOutputModel);
		logger.info("makeReservation() method ended.");
		return new ResponseEntity<ReservationOutputModel>(reservationOutputModel, HttpStatus.OK);
	}

	@PutMapping(value = "/cancelreservation/{reservationId}")
	public ResponseEntity<CancellationOutputModel> cancelReservation(
			@PathVariable("reservationId") Integer reservationId)
			throws InvalidCancellationException, InvalidReservationException {
		logger.info("cancelReservation() method started.");
		CancellationOutputModel cancellationOutputModel = reservationService.cancelReservation(reservationId);
		logger.info("Cancellation Details: {}", cancellationOutputModel);
		logger.info("cancelReservation() method ended.");
		return new ResponseEntity<CancellationOutputModel>(cancellationOutputModel, HttpStatus.OK);
	}

	@GetMapping(value = "/getallreservationsbycustomerid/{customerId}")
	public ResponseEntity<List<CancellationOutputModel>> getAllReservationsByCustomerId(
			@PathVariable("customerId") Integer customerId)
			throws CustomerNotFoundException, InvalidReservationException {
		logger.info("getAllReservationsByCustomerId() method started.");
		List<CancellationOutputModel> cancellationOutputModelList = reservationService
				.getAllReservationsByCustomerId(customerId);
		logger.info("Reservations Lists: {}", cancellationOutputModelList);
		logger.info("getAllReservationsByCustomerId() method ended.");
		return new ResponseEntity<List<CancellationOutputModel>>(cancellationOutputModelList, HttpStatus.OK);
	}

	@GetMapping(value = "/totalnoofreservations/{trainNo}/{travelDate}/{seatType}")
	public ResponseEntity<Integer> getTotalNoOfReservations(@PathVariable("trainNo") String trainNo, @PathVariable("travelDate") String travelDate,
			@PathVariable("seatType") String seatType) {
		logger.info("getTotalNoOfReservations() method started.");
		Integer totalNoOfReservations = reservationService.getTotalNoOfReservationsByDateAndSeatType(trainNo, travelDate, seatType);
		logger.info("No of Reservations: {}", totalNoOfReservations);
		logger.info("getTotalNoOfReservations() method ended.");
		return new ResponseEntity<Integer>(totalNoOfReservations, HttpStatus.OK);
	}
}
