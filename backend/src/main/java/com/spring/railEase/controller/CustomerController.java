package com.spring.railEase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.railEase.model.CustomerInputModel;
import com.spring.railEase.model.CustomerOutputModel;
import com.spring.railEase.service.CustomerService;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCustomerException;

@RestController
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value="/addcustomer")
	public ResponseEntity<CustomerOutputModel> addCustomer(@RequestBody CustomerInputModel customerInputModel) throws InvalidCustomerException {
		logger.info("addCustomer() method started.");
		CustomerOutputModel customerOutputModel = customerService.addCustomer(customerInputModel);
		logger.info("Customer: {}",customerOutputModel);
		logger.info("addCustomer() method ended.");
		return new ResponseEntity<CustomerOutputModel>(customerOutputModel,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/getcustomerbyid/{customerId}")
	public ResponseEntity<CustomerOutputModel> getCustomerById(@PathVariable("customerId") Integer customerId) throws CustomerNotFoundException {
		logger.info("getCustomerById() method started.");
		CustomerOutputModel customerOutputModel = customerService.getCustomerById(customerId);
		logger.info("Customer: {}",customerOutputModel);
		logger.info("getCustomerById() method ended.");
		return new ResponseEntity<CustomerOutputModel>(customerOutputModel,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/customerlogin/{email}/{password}")
	public ResponseEntity<CustomerOutputModel> customerLogin(@PathVariable("email") String email,@PathVariable("password") String password) throws InvalidCustomerException{
		logger.info("customerLogin() started");
		CustomerOutputModel customerOutputModel= customerService.customerLogin(email, password);
		 logger.info("Customer:{}",customerOutputModel);
		 logger.info("customerLogin() ended");
		 return new ResponseEntity<CustomerOutputModel>(customerOutputModel,HttpStatus.OK);
	}

}
