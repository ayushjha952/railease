package com.spring.railEase.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.railEase.entity.CancelledReservation;
import com.spring.railEase.entity.Customer;
import com.spring.railEase.entity.Passenger;
import com.spring.railEase.entity.Reservation;
import com.spring.railEase.entity.Train;
import com.spring.railEase.model.CancellationOutputModel;
import com.spring.railEase.model.PassengerInputModel;
import com.spring.railEase.model.ReservationInputModel;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.repository.CancelledReservationRepository;
import com.spring.railEase.repository.CustomerRepository;
import com.spring.railEase.repository.PassengerRepository;
import com.spring.railEase.repository.ReservationRepository;
import com.spring.railEase.repository.TrainRepository;
import com.spring.railEase.service.PassengerServiceImpl;
import com.spring.railEase.service.ReservationServiceImpl;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCancellationException;
import com.spring.railEase.service.exception.InvalidReservationException;
import com.spring.railEase.service.exception.TrainNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private TrainRepository trainRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private PassengerRepository passengerRepository;

	@Mock
	private PassengerServiceImpl passengerService;

	@Mock
	private CancelledReservationRepository cancelledReservationRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	private ReservationInputModel reservationInputModel;
	private Reservation reservation;
	private Train train;
	private Customer customer;
	private List<PassengerInputModel> passengerInputModelList;
	private Passenger passenger;

	@Test
	void testMakeReservation()
			throws InvalidReservationException, TrainNotFoundException, CustomerNotFoundException {
	
		reservationInputModel = new ReservationInputModel();
		reservationInputModel.setTrainNo("123");
		reservationInputModel.setSeatType("ac1");
		reservationInputModel.setTravelDate("2023-08-07");
		reservationInputModel.setSource("Source");
		reservationInputModel.setDestination("Destination");
		reservationInputModel.setNoOfSeats(2);
		reservationInputModel.setCustomerId(1);
		

		passengerInputModelList = new ArrayList<>();
		passengerInputModelList.add(new PassengerInputModel("Passenger 1", "Male", 25));
		passengerInputModelList.add(new PassengerInputModel("Passenger 2", "Female", 30));
		reservationInputModel.setPassengerList(passengerInputModelList);

		customer = new Customer();
		customer.setCustomerId(1);

		train = new Train();
		train.setTrainNo("123");
		train.setAc1Seats(10);
		train.setAc1fare(50);
		train.setArrivalTime("09:00:00");
		train.setDepartureTime("10:00:00");

		passenger = new Passenger();
		passenger.setPassengerId(1);
		passenger.setPassengerName("Passenger 1");
		passenger.setAge(25);
		passenger.setGender("Male");

		List<Passenger> passengers = new ArrayList<>();
		passengers.add(passenger);

		reservation = new Reservation();
		reservation.setReservationId(1);
		reservation.setBookingStatus("Booked");
		reservation.setTrain(train);
		reservation.setCustomer(customer);
		reservation.setSeatType("ac1");
		reservation.setTotalTicketPrice(100);
		reservation.setNoOfSeats(2);
		reservation.setCustomer(customer);
		reservation.setTrain(train);
		reservation.setPassengerList(passengers);

		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
		when(trainRepository.findById(anyString())).thenReturn(Optional.of(train));
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

		ReservationOutputModel result = reservationService.makeReservation(reservationInputModel);

		assertNotNull(result);
		assertEquals(1, result.getReservationId());
		assertEquals("Booked", result.getBookingStatus());
		assertEquals(2, result.getNoOfSeats());
		assertEquals(100, result.getTotalTicketPrice());
		assertEquals("ac1", result.getSeatType());
		verify(reservationRepository, times(1)).save(any(Reservation.class));
		verify(passengerRepository, times(2)).save(any(Passenger.class));
	}

	@Test
	void testCancelReservation()
			throws InvalidCancellationException, InvalidReservationException {
		customer = new Customer();
		customer.setCustomerId(1);

		train = new Train();
		train.setTrainNo("123");
		train.setAc1Seats(10);
		train.setAc1fare(50);
		train.setArrivalTime("09:00:00");
		train.setDepartureTime("10:00:00");

		passenger = new Passenger();
		passenger.setPassengerId(1);
		passenger.setPassengerName("Passenger 1");
		passenger.setAge(25);
		passenger.setGender("Male");

		List<Passenger> passengers = new ArrayList<>();
		passengers.add(passenger);

		Reservation reservation = new Reservation();
		reservation.setReservationId(1);
		reservation.setTotalTicketPrice(100);
		reservation.setSeatType("ac2");
		reservation.setBookingStatus("Booked");
		reservation.setTravelDate("2023-08-07"); // Set a valid travel date
		reservation.setCustomer(customer);
		reservation.setTrain(train);
		reservation.setPassengerList(passengers);
		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

		when(reservationRepository.findById(anyInt())).thenReturn(Optional.of(reservation));
		when(cancelledReservationRepository.save(any(CancelledReservation.class)))
				.thenReturn(new CancelledReservation());
	
		CancellationOutputModel result = reservationService.cancelReservation(1);

		assertNotNull(result);
//		assertEquals(25, result.getRefundAmount().intValue()); 
																
		assertNotNull(result.getReservationOutputModel());
		assertEquals(1, result.getReservationOutputModel().getReservationId());
		assertEquals("Cancelled", result.getReservationOutputModel().getBookingStatus());

		verify(reservationRepository, times(1)).save(any(Reservation.class));
		verify(cancelledReservationRepository, times(1)).save(any(CancelledReservation.class));
		verify(passengerService, times(1)).removePassengerByReservationId(1);
	}

	@Test
	public void testGetAllReservationsByCustomerId()
			throws CustomerNotFoundException, InvalidReservationException {
		
		Integer customerId = 1; 
		Customer customer = new Customer();
		customer.setCustomerId(customerId);

		train = new Train();
		train.setTrainNo("123");
		train.setAc1Seats(10);
		train.setAc1fare(50);
		train.setArrivalTime("09:00:00");
		train.setDepartureTime("10:00:00");

		passenger = new Passenger();
		passenger.setPassengerId(1);
		passenger.setPassengerName("Passenger 1");
		passenger.setAge(25);
		passenger.setGender("Male");

		List<Passenger> passengers = new ArrayList<>();
		passengers.add(passenger);

		Reservation reservation1 = new Reservation();
		reservation1.setReservationId(1);
		reservation1.setBookingStatus("Cancelled");
		reservation1.setCustomer(customer);
		reservation1.setTrain(train);
		reservation1.setPassengerList(passengers);
		reservation1.setTravelDate("2023-08-07");

		Reservation reservation2 = new Reservation();
		reservation2.setReservationId(2);
		reservation2.setBookingStatus("Active");
		reservation2.setCustomer(customer);
		reservation2.setTrain(train);
		reservation2.setPassengerList(passengers);
		reservation2.setTravelDate("2023-08-07");

		CancelledReservation cancelledReservation1 = new CancelledReservation();
		cancelledReservation1.setCancellationId(101);
		cancelledReservation1.setRefundAmount(100);
		cancelledReservation1.setReservation(reservation1);

		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation1);
		reservations.add(reservation2);

		List<CancelledReservation> cancelledReservations = new ArrayList<>();
		cancelledReservations.add(cancelledReservation1);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(reservationRepository.getAllReservationsByCustomerId(customerId)).thenReturn(Optional.of(reservations));
		when(cancelledReservationRepository.getCancellationDetailsByReservationId(1)).thenReturn(cancelledReservation1);
		
		List<CancellationOutputModel> result = reservationService.getAllReservationsByCustomerId(customerId);

		assertEquals(2, result.size());
										
		CancellationOutputModel cancellationOutputModel = result.get(0);
		assertEquals(101, cancellationOutputModel.getCancellationId());
		assertEquals(100, cancellationOutputModel.getRefundAmount());
		assertNotNull(cancellationOutputModel.getReservationOutputModel());
		assertNull(cancellationOutputModel.getReservationOutputModel().getPassengerList());
	}

	@Test
	public void testGetTotalNoOfReservationsByDateAndSeatType() {
	
		String trainNo = "123";
		String travelDate = "2023-08-07"; 
		String seatType = "AC1"; 

		int expectedTotal = 5;

		when(reservationRepository.getTotalNoOfBookings(trainNo, travelDate, seatType))
				.thenReturn(Optional.of(expectedTotal));

		int result = reservationService.getTotalNoOfReservationsByDateAndSeatType(trainNo, travelDate, seatType);

		assertEquals(expectedTotal, result);
	}

	@Test
	public void testGetAllReservationsByCustomerIdWithInvalidCustomerId() {
		
		Integer customerId = 100; 

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> {
			reservationService.getAllReservationsByCustomerId(customerId);
		});
	}

	@Test
	public void testGetReservationByReservationIdWithInvalidReservationId() {

		Integer reservationId = 100; 

		when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

		assertThrows(InvalidReservationException.class, () -> {
			reservationService.findReservationById(reservationId);
		});
	}
	
	@Test
	public void testMakeReservationWithInvalidTrainNo() throws InvalidReservationException, TrainNotFoundException, CustomerNotFoundException {
	    
	    ReservationInputModel reservationInputModel = new ReservationInputModel();
	    reservationInputModel.setTrainNo("12376"); 

	    when(trainRepository.findById(reservationInputModel.getTrainNo())).thenReturn(Optional.empty());

	    assertThrows(TrainNotFoundException.class, () -> {
	        reservationService.makeReservation(reservationInputModel);
	    });
	}

}
