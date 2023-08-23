package com.spring.railEase.service;

import org.springframework.stereotype.Service;

import com.spring.railEase.model.CustomerInputModel;
import com.spring.railEase.model.CustomerOutputModel;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCustomerException;

@Service
public interface CustomerService {
	
	public CustomerOutputModel addCustomer(CustomerInputModel customerInputModel) throws InvalidCustomerException;
	
	public CustomerOutputModel getCustomerById(Integer customerId) throws CustomerNotFoundException;
	
	public CustomerOutputModel customerLogin(String email,String password) throws InvalidCustomerException;
}
