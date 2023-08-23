package com.spring.railEase.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.railEase.entity.Customer;
import com.spring.railEase.model.CustomerInputModel;
import com.spring.railEase.model.CustomerOutputModel;
import com.spring.railEase.repository.CustomerRepository;
import com.spring.railEase.service.CustomerServiceImpl;
import com.spring.railEase.service.exception.CustomerNotFoundException;
import com.spring.railEase.service.exception.InvalidCustomerException;
import com.spring.railEase.validation.CustomerValidation;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

		@Mock
		private CustomerRepository customerRepository;
		
		@Mock
		private CustomerValidation customerValidation;
		
		@InjectMocks
		private CustomerServiceImpl customerService;
		
		
		@Test
		public void testAddCustomer() throws InvalidCustomerException {
			
			CustomerInputModel inputModel = new CustomerInputModel();
			inputModel.setFirstName("John");
			inputModel.setLastName("Doe");
			inputModel.setEmail("johndoe@example.com");
			inputModel.setPassword("Abcde123");
			inputModel.setContactNo("1234567890");
			inputModel.setAddress("123 Main St");
			
			when(customerValidation.validateEmail(inputModel.getEmail())).thenReturn(true);
			when(customerValidation.validatePass(inputModel.getPassword())).thenReturn(true);
			when(customerValidation.validatePhoneNumber(inputModel.getContactNo())).thenReturn(true);
			
			CustomerOutputModel outputModel = customerService.addCustomer(inputModel);
			
			assertNotNull(outputModel);
			assertEquals(inputModel.getFirstName(), outputModel.getFirstName());
			assertEquals(inputModel.getLastName(), outputModel.getLastName());
			assertEquals(inputModel.getEmail(), outputModel.getEmail());
			assertEquals(inputModel.getContactNo(), outputModel.getContactNo());
			assertEquals(inputModel.getAddress(), outputModel.getAddress());
			
			verify(customerRepository, times(1)).save(any(Customer.class));
		}
		
		@Test
		public void testAddCustomerInvalidInput() throws InvalidCustomerException {
			CustomerInputModel inputModel = new CustomerInputModel();
			
			when(customerValidation.validateEmail(inputModel.getEmail())).thenReturn(false);
			
			assertThrows(InvalidCustomerException.class, () -> {
				customerService.addCustomer(inputModel);
			});
			
			verify(customerRepository, never()).save(any(Customer.class));
		}
		
		@Test
		public void testGetCustomerById() throws CustomerNotFoundException {
			
			Integer customerId = 1;
			Customer customer = new Customer();
			
			when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
			
			CustomerOutputModel outputModel = customerService.getCustomerById(customerId);
			
			assertNotNull(outputModel);
			
			verify(customerRepository, times(1)).findById(customerId);
		}
		
		@Test
		public void testGetCustomerByIdInvalidId() throws CustomerNotFoundException {
			
			Integer customerId = 1;
			
			when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
			
			assertThrows(CustomerNotFoundException.class, () -> {
				customerService.getCustomerById(customerId);
			});
			
			verify(customerRepository, times(1)).findById(customerId);
		}
		
		@Test
		public void testCustomerLogin() throws InvalidCustomerException {
			
			String email = "johndoe@example.com";
			String password = "Abcde123";
			Customer customer = new Customer();
			customer.setPassword(password);
			
			when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
			
			CustomerOutputModel outputModel = customerService.customerLogin(email, password);
			
			assertNotNull(outputModel);
			
			verify(customerRepository, times(1)).findCustomerByEmail(email);
		}
		
		@Test
		public void testCustomerLoginInvalidCredentials() throws InvalidCustomerException {
		
			String email = "johndoe@example.com";
			String password = "password";
			
			when(customerRepository.findCustomerByEmail(email)).thenReturn(null);
			
			assertThrows(InvalidCustomerException.class, () -> {
				customerService.customerLogin(email, password);
			});
			
			verify(customerRepository, times(1)).findCustomerByEmail(email);
		}

}
