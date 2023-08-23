package com.spring.railEase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainOutputModel {
	private String trainNo;
	private String trainName; 
	private String source;
	private String destination; 
	private Integer ac1Seats; 
	private Integer ac2seats; 
	private Integer slSeats; 
	private Integer ac1fare; 
	private Integer ac2fare; 
	private Integer slfare; 
	private String departureTime; 
	private String arrivalTime; 
	private String duration;
}
