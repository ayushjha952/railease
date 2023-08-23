package com.spring.railEase.util;

import com.spring.railEase.entity.Passenger;
import com.spring.railEase.model.PassengerOutputModel;

public class MapToPassengerOutput {
	public PassengerOutputModel mapToPassenger(Passenger passenger) {
		PassengerOutputModel passengerOutputModel=new PassengerOutputModel();
		passengerOutputModel.setAge(passenger.getAge());
		passengerOutputModel.setGender(passenger.getGender());
		passengerOutputModel.setPassengerName(passenger.getPassengerName());
		return passengerOutputModel;
	}
}
