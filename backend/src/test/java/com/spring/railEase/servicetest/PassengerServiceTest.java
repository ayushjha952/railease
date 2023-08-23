package com.spring.railEase.servicetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.railEase.entity.Passenger;
import com.spring.railEase.repository.PassengerRepository;
import com.spring.railEase.service.PassengerServiceImpl;


@ExtendWith(SpringExtension.class)
public class PassengerServiceTest {
	
	@Mock
	private PassengerRepository passengerRepository;
	
	@InjectMocks
	private PassengerServiceImpl passengerService;
	
	@Test
	public void testAddPassenger() {
		// Arrange
		Passenger passenger = new Passenger();
		passenger.setAge(22);
		passenger.setGender("male");
		passenger.setPassengerId(1);
		passenger.setPassengerName("Ayush");
		when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);
		
		// Act
		Passenger result = passengerService.addPassenger(passenger);
		
		// Assert
		assertEquals(1, result.getPassengerId());
		assertEquals("Ayush", result.getPassengerName());
		assertEquals(22, result.getAge());
		assertEquals("male", result.getGender());
	}
	
	@Test
	public void testRemovePassengerByReservationId() {
		
		Integer reservationId = 123;
		
		passengerService.removePassengerByReservationId(reservationId);

		verify(passengerRepository).removePassengerByReservationId(reservationId);
	}

}
