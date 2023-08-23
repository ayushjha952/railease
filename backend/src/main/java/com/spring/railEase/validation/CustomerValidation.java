package com.spring.railEase.validation;

import org.springframework.stereotype.Component;

import com.spring.railEase.service.exception.InvalidCustomerException;

@Component
public class CustomerValidation {
	public boolean validatePass(String password) throws InvalidCustomerException {
		if (password.length() < 6) {
	        throw new InvalidCustomerException("Password is too short");
	    } else if (!password.matches(".*[a-z].*")) {
	        throw new InvalidCustomerException("Password should contain at least one lowercase letter");
	    } else if (!password.matches(".*[A-Z].*")) {
	        throw new InvalidCustomerException("Password should contain at least one uppercase letter");
	    } else if (!password.matches(".*\\d.*")) {
	        throw new InvalidCustomerException("Password should contain at least one number");
	    } else if (!password.matches(".*[^a-zA-Z0-9].*")) {
	        throw new InvalidCustomerException("Password should contain at least one special character");
	    }
	    return true;
	}
	
	
	public boolean validateEmail(String email) throws InvalidCustomerException {
		if(email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			return true;
		}
		else {
			throw new InvalidCustomerException("Invalid Email Id.");
		}
	}
	
	public boolean validatePhoneNumber(String contactNo) throws InvalidCustomerException {
		if(contactNo.length()<10 || contactNo.length()>10) {
			throw new InvalidCustomerException("Customer Contact number should be of 10 digit.");
		}
		else if(!contactNo.matches("[0-9]+")) {
			throw new InvalidCustomerException("Customer Contact number should contains only Integers.");
		}
		
		return true;
	}
	
//	public boolean checkCustomerExists(String email) {
//        Customer customer = customerRepository.findByEmail(email);
//        return customer != null;
//    }
}
