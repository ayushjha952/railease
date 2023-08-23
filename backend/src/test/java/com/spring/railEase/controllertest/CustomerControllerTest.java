package com.spring.railEase.controllertest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.railEase.controller.CustomerController;
import com.spring.railEase.model.CustomerInputModel;
import com.spring.railEase.model.CustomerOutputModel;
import com.spring.railEase.service.CustomerServiceImpl;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	
	@MockBean
	private CustomerServiceImpl customerServiceImpl;
	
	@Mock
	private CustomerController customerController;
	
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	public void addCustomerTest() throws Exception {
		
		CustomerInputModel customerInputModel = new CustomerInputModel();
		
		customerInputModel.setFirstName("Ayush");
		customerInputModel.setLastName("Jha");
		customerInputModel.setEmail("ayush922@gmail.com");
		customerInputModel.setPassword("Abcde12345");
		
		CustomerOutputModel customerOutputModel = new CustomerOutputModel();
		customerOutputModel.setCustumerId(1);
		customerOutputModel.setFirstName("Ayush");
		customerOutputModel.setLastName("Jha");
		customerOutputModel.setEmail("ayush922@gmail.com");
	    
	    when(customerServiceImpl.addCustomer(customerInputModel)).thenReturn(customerOutputModel);

	    mockMvc.perform(MockMvcRequestBuilders.post("/addcustomer").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerInputModel))).andExpect(status().isOk());
	}
	
	@Test
	public void getCustomerByIdTest() throws Exception {
		CustomerOutputModel customerOutputModel = new CustomerOutputModel();
		customerOutputModel.setCustumerId(1);
		customerOutputModel.setFirstName("Ayush");
		customerOutputModel.setLastName("Jha");
		customerOutputModel.setEmail("ayush922@gmail.com");
		
		when(customerServiceImpl.getCustomerById(1)).thenReturn(customerOutputModel);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/getcustomerbyid/{customerId}",1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\r\n"
						+ "  \"custumerId\": 1,\r\n"
						+ "  \"firstName\": \"Ayush\",\r\n"
						+ "  \"lastName\": \"Jha\",\r\n"
						+ "  \"email\": \"ayush922@gmail.com\"\r\n"
						+ "}"));
	}
	
	@Test
	public void customerLoginTest() throws Exception {
		CustomerOutputModel customerOutputModel = new CustomerOutputModel();
		customerOutputModel.setCustumerId(1);
		customerOutputModel.setFirstName("Ayush");
		customerOutputModel.setLastName("Jha");
		customerOutputModel.setEmail("ayush922@gmail.com");

		when(customerServiceImpl.customerLogin("ayush922@gmail.com","Abcde12345")).thenReturn(customerOutputModel);

		mockMvc.perform(MockMvcRequestBuilders.get("/customerlogin/{email}/{password}","ayush922@gmail.com","Abcde12345")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("{\r\n"
				+ "  \"custumerId\": 1,\r\n"
				+ "  \"firstName\": \"Ayush\",\r\n"
				+ "  \"lastName\": \"Jha\",\r\n"
				+ "  \"email\": \"ayush922@gmail.com\"\r\n"
				+ "}"));
	}
}
