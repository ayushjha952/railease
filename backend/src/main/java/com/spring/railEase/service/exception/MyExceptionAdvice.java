package com.spring.railEase.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.spring.railEase.model.ErrorResponse;

@ControllerAdvice
public class MyExceptionAdvice {
	
	@ExceptionHandler(value=CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e , WebRequest w){
		ErrorResponse er = new ErrorResponse();
		er.setErrorCode(404);
		er.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorResponse>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=TrainNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTrainNotFoundException(TrainNotFoundException e , WebRequest w){
		ErrorResponse er = new ErrorResponse();
		er.setErrorCode(404);
		er.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorResponse>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=InvalidReservationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidReservationException(InvalidReservationException e , WebRequest w){
		ErrorResponse er = new ErrorResponse();
		er.setErrorCode(404);
		er.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorResponse>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=InvalidCancellationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCancellationException(InvalidCancellationException e , WebRequest w){
		ErrorResponse er = new ErrorResponse();
		er.setErrorCode(404);
		er.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorResponse>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=InvalidCustomerException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCustomerException(InvalidCustomerException e , WebRequest w){
		ErrorResponse er = new ErrorResponse();
		er.setErrorCode(404);
		er.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorResponse>(er,HttpStatus.NOT_FOUND);
	}
}
