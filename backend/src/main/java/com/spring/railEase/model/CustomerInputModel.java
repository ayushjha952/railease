package com.spring.railEase.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInputModel {
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String contactNo;
}
