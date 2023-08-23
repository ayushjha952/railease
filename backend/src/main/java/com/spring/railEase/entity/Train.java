package com.spring.railEase.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Train {
	@Id
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
	@OneToMany(mappedBy = "train")
	@ToString.Exclude
	private List<Reservation> trainreservationList;
}
