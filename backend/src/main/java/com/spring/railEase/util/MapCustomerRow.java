package com.spring.railEase.util;

import com.spring.railEase.entity.Customer;
import com.spring.railEase.model.CustomerOutputModel;

public class MapCustomerRow {
	public CustomerOutputModel mapToCustomerOutput(Customer customer)
	{
		CustomerOutputModel customerOutputModel =new CustomerOutputModel();
		customerOutputModel.setAddress(customer.getAddress());
		customerOutputModel.setContactNo(customer.getContactNo());
		customerOutputModel.setCustumerId(customer.getCustomerId());
		customerOutputModel.setFirstName(customer.getFirstName());
		customerOutputModel.setLastName(customer.getLastName());
		customerOutputModel.setEmail(customer.getEmail());
		return customerOutputModel;
	}
}
