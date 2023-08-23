package com.spring.railEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Customer;
import com.spring.railEase.model.CustomerInputModel;
import com.spring.railEase.model.CustomerOutputModel;
import com.spring.railEase.repository.CustomerRepository;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCustomerException;
import com.spring.railEase.util.MapCustomerRow;
import com.spring.railEase.validation.CustomerValidation;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerValidation customerValidation;
	
	public CustomerOutputModel addCustomer(CustomerInputModel customerInputModel) throws InvalidCustomerException {
		
		if(!customerValidation.validateEmail(customerInputModel.getEmail())) {
			throw new InvalidCustomerException();
		}
	
		if(!customerValidation.validatePass(customerInputModel.getPassword())) {
			throw new InvalidCustomerException();
		}
	
		if(!customerValidation.validatePhoneNumber(customerInputModel.getContactNo())) {
			throw new InvalidCustomerException();
		}
		
		Customer customer1 = customerRepository.findCustomerByEmail(customerInputModel.getEmail());
		if(customer1!=null)
			throw new InvalidCustomerException("Account with this email already Exists..");
				
		
		Customer customer =new Customer();
		customer.setAddress(customerInputModel.getAddress());
		customer.setContactNo(customerInputModel.getContactNo());
		customer.setFirstName(customerInputModel.getFirstName());
		customer.setLastName(customerInputModel.getLastName());
		customer.setPassword(customerInputModel.getPassword());
		customer.setEmail(customerInputModel.getEmail());
		customerRepository.save(customer);
		CustomerOutputModel customerOutputModel = new MapCustomerRow().mapToCustomerOutput(customer);
		return customerOutputModel;
	}
	
	public CustomerOutputModel getCustomerById(Integer customerId) throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("No such customer exists");
		CustomerOutputModel customerOutputModel = new MapCustomerRow().mapToCustomerOutput(customer);
		return  customerOutputModel;
	}
	
	public CustomerOutputModel customerLogin(String email,String password) throws InvalidCustomerException {
    	Customer customer=customerRepository.findCustomerByEmail(email);
    	if(customer==null) {
    		throw new InvalidCustomerException("Please enter correct EmailId and password");
    	}
    	if(!customer.getPassword().equals(password)) {
    		throw new InvalidCustomerException("Please enter correct EmailId and password");
    	}
    	
    	CustomerOutputModel customerOutputModel= new MapCustomerRow().mapToCustomerOutput(customer);
    	
    	return customerOutputModel;
    }
}
