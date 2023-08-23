package com.spring.railEase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerOutputModel {
	private Integer custumerId;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String contactNo;
}
