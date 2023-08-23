package com.spring.railEase.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.railEase.entity.CancelledReservation;
import com.spring.railEase.entity.Customer;
import com.spring.railEase.entity.Passenger;
import com.spring.railEase.entity.Reservation;
import com.spring.railEase.entity.Train;
import com.spring.railEase.model.ReservationInputModel;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.repository.CancelledReservationRepository;
import com.spring.railEase.repository.CustomerRepository;
import com.spring.railEase.repository.PassengerRepository;
import com.spring.railEase.repository.ReservationRepository;
import com.spring.railEase.repository.TrainRepository;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCancellationException;
import com.spring.railEase.service.exception.InvalidReservationException;
import com.spring.railEase.service.exception.TrainNotFoundException;
import com.spring.railEase.util.MapToCancellationOutput;
import com.spring.railEase.util.MapToReservationOutput;
import com.spring.railEase.model.CancellationOutputModel;
import com.spring.railEase.model.PassengerInputModel;

import jakarta.transaction.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	TrainService trainService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CancelledReservationRepository cancelledReservationRepository;
	@Autowired
	PassengerService passengerService;
	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TrainRepository trainRepository;

	@Transactional
	public ReservationOutputModel makeReservation(ReservationInputModel reservationInputModel)
			throws InvalidReservationException, TrainNotFoundException, CustomerNotFoundException {

		Train train = trainRepository.findById(reservationInputModel.getTrainNo()).orElse(null);
		if (train == null)
			throw new TrainNotFoundException("Train is not avaiable......");
		Customer customer = customerRepository.findById(reservationInputModel.getCustomerId()).orElse(null);
		if (customer == null)
			throw new CustomerNotFoundException("No such customer exists");
		int availableTrainSeats = 0, ticketFare = 0;
		if (reservationInputModel.getSeatType().equalsIgnoreCase("ac1")) {
			availableTrainSeats = train.getAc1Seats();
			ticketFare = train.getAc1fare();
		} else if (reservationInputModel.getSeatType().equalsIgnoreCase("ac2")) {
			availableTrainSeats = train.getAc2seats();
			ticketFare = train.getAc2fare();
		} else if (reservationInputModel.getSeatType().equalsIgnoreCase("sl")) {
			availableTrainSeats = train.getSlSeats();
			ticketFare = train.getSlfare();
		}

		Optional<Integer> reservations = reservationRepository.getTotalNoOfBookings(reservationInputModel.getTrainNo(),
				reservationInputModel.getTravelDate(), reservationInputModel.getSeatType());
		int totalReservations = 0;
		if (reservations.isPresent())
			totalReservations = reservations.get();
		if ((totalReservations + reservationInputModel.getNoOfSeats()) > availableTrainSeats)
			throw new InvalidReservationException("Can't book train tickets due to insufficeint seats.");
		Reservation reservation = new Reservation();
		reservation.setReservationDate(LocalDate.now());
		reservation.setTravelDate(reservationInputModel.getTravelDate());

		if (LocalDate.parse(reservation.getTravelDate()).isBefore(reservation.getReservationDate()))
			throw new InvalidReservationException("Please enter a valid Travel Date");

		if (LocalDate.parse(reservation.getTravelDate()).isEqual(reservation.getReservationDate())) {
			LocalTime currentTime = LocalTime.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime arrivalTime = LocalTime.parse(train.getArrivalTime(), formatter);
	        LocalTime departureTime = LocalTime.parse(train.getDepartureTime(), formatter);

			Duration duration = Duration.between(currentTime, arrivalTime);
			long minuteDifference = duration.toMinutes();
			if (minuteDifference <= 240 && !(currentTime.isAfter(departureTime)))
				throw new InvalidReservationException("Can't book tickets after the chart has been prepared...");
			if(currentTime.isAfter(departureTime))
				throw new InvalidReservationException("Can't book tickets after train has left the station...");
		}
		reservation.setTrain(train);
		reservation.setDestination(reservationInputModel.getDestination());
		reservation.setSource(reservationInputModel.getSource());

		List<PassengerInputModel> passengerInputModelList = reservationInputModel.getPassengerList();
		List<Passenger> passengerList = new ArrayList<Passenger>();
		for (PassengerInputModel passengerInputModel : passengerInputModelList) {
			Passenger passenger = new Passenger();
			passenger.setAge(passengerInputModel.getAge());
			passenger.setGender(passengerInputModel.getGender());
			passenger.setPassengerName(passengerInputModel.getPassengerName());
			passenger.setReservation(reservation);
			passengerList.add(passenger);
			passengerRepository.save(passenger);
		}

		reservation.setSeatType(reservationInputModel.getSeatType());
		reservation.setPassengerList(passengerList);
		reservation.setBookingStatus("Booked");
		reservation.setTotalTicketPrice(reservationInputModel.getNoOfSeats() * ticketFare);
		reservation.setNoOfSeats(reservationInputModel.getNoOfSeats());
		reservation.setCustomer(customer);
		reservation = reservationRepository.save(reservation);
		ReservationOutputModel reservationOutputModel = new MapToReservationOutput().mapToReservation(reservation);
		return reservationOutputModel;
	}

	@Transactional
	public CancellationOutputModel cancelReservation(Integer reservationId)
			throws InvalidCancellationException, InvalidReservationException {
		Reservation reservation = findReservationById(reservationId);
		Integer price = reservation.getTotalTicketPrice();
		LocalDate journeyDate = LocalDate.parse(reservation.getTravelDate());
		LocalDate currentDate = LocalDate.now();
		if (reservation.getBookingStatus().equalsIgnoreCase("Cancelled"))
			throw new InvalidCancellationException("Ticket is already Cancelled");
		
		LocalDate threedaysBefore = journeyDate.minusDays(3);
		LocalDate sevendaysBefore = journeyDate.minusDays(7);
		
		if (currentDate.isAfter(journeyDate)||currentDate.isEqual(journeyDate))
		{
			throw new InvalidCancellationException("Cannot cancel the ticket on or after journey date");
		}

		else if (currentDate.isAfter(threedaysBefore) && currentDate.isBefore(journeyDate))
			price = (int) (0.50 * price);

		else if ((currentDate.isAfter(sevendaysBefore) && currentDate.isBefore(threedaysBefore)) 
				|| currentDate.isEqual(sevendaysBefore) || currentDate.isEqual(threedaysBefore))
			price = (int) (0.25 * price);

		else if(currentDate.isBefore(sevendaysBefore))
			price = 0;
		reservation.setBookingStatus("Cancelled");
		reservation = reservationRepository.save(reservation);

		CancelledReservation cancelledReservation = new CancelledReservation();
		cancelledReservation.setRefundAmount(price);
		cancelledReservation.setReservation(reservation);
		cancelledReservationRepository.save(cancelledReservation);

		passengerService.removePassengerByReservationId(reservation.getReservationId());
		CancellationOutputModel cancellationOutputModel = new MapToCancellationOutput()
				.mapToCancellation(cancelledReservation);

		return cancellationOutputModel;
	}

	public List<CancellationOutputModel> getAllReservationsByCustomerId(Integer customerId)
			throws CustomerNotFoundException, InvalidReservationException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null)
			throw new CustomerNotFoundException("No such customer exists");
		Optional<List<Reservation>> reservationsList = reservationRepository.getAllReservationsByCustomerId(customerId);
		if (!reservationsList.isPresent() || reservationsList.get().isEmpty())
			throw new InvalidReservationException("No reservations where made by the customer");

		List<CancellationOutputModel> cancellationOutputModelList = new ArrayList<CancellationOutputModel>();

		for (Reservation reservations : reservationsList.get()) {
			if (reservations.getBookingStatus().equalsIgnoreCase("Cancelled")) {
				CancelledReservation cancelledReservation = cancelledReservationRepository
						.getCancellationDetailsByReservationId(reservations.getReservationId());

				CancellationOutputModel cancellationOutputModel = new MapToCancellationOutput()
						.mapToCancellation(cancelledReservation);

				cancellationOutputModel.getReservationOutputModel().setPassengerList(null);
				cancellationOutputModelList.add(cancellationOutputModel);
			} else {
				CancelledReservation cancelledReservation = new CancelledReservation();
				cancelledReservation.setCancellationId(null);
				cancelledReservation.setRefundAmount(null);
				cancelledReservation.setReservation(reservations);
				CancellationOutputModel cancellationOutputModel = new MapToCancellationOutput()
						.mapToCancellation(cancelledReservation);
				cancellationOutputModel.getReservationOutputModel().setPassengerList(null);
				cancellationOutputModelList.add(cancellationOutputModel);
			}
		}
		return cancellationOutputModelList;
	}

	public Reservation findReservationById(Integer reservationId) throws InvalidReservationException {
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		if (reservation == null)
			throw new InvalidReservationException("No such reservation was made");
		return reservation;
	}

	public Integer getTotalNoOfReservationsByDateAndSeatType(String trainNo, String travelDate, String seatType) {
		return reservationRepository.getTotalNoOfBookings(trainNo, travelDate, seatType).orElse(0);
	}
}
