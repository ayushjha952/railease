package com.spring.railEase.servicetest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.railEase.entity.Train;
import com.spring.railEase.model.TrainOutputModel;
import com.spring.railEase.repository.TrainRepository;
import com.spring.railEase.service.TrainServiceImpl;
import com.spring.railEase.service.exception.TrainNotFoundException;


@ExtendWith(SpringExtension.class)
public class TrainServiceTest {
	
	@Mock
	private TrainRepository trainRepository;
	
	@InjectMocks
	private TrainServiceImpl trainService;
	
	
	@Test
	public void testGetTrainByTrainNo() throws TrainNotFoundException {
		
		Train train = new Train();
		train.setTrainNo("123");
		when(trainRepository.findById("123")).thenReturn(Optional.of(train));
		
		TrainOutputModel result = trainService.getTrainByTrainNo("123");
		
		assertNotNull(result);
		assertEquals("123", result.getTrainNo());
	}
	
	@Test
	public void testGetTrainByTrainNoNotFound() {
		
		when(trainRepository.findById("123")).thenReturn(Optional.empty());
		
		assertThrows(TrainNotFoundException.class, () -> {
			trainService.getTrainByTrainNo("123");
		});
	}
	
	@Test
	public void testGetTrainBySouceAndDestination() throws TrainNotFoundException {
		
		String source = "Source";
		String destination = "Destination";
		List<Train> trainList = new ArrayList<>();
		trainList.add(new Train());
		when(trainRepository.getTrainBySouceAndDestination(source, destination)).thenReturn(trainList);
		
		List<TrainOutputModel> result = trainService.getTrainBySouceAndDestination(source, destination);
		
		assertNotNull(result);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGetTrainBySouceAndDestinationNotFound() {
		
		String source = "Source";
		String destination = "Destination";
		when(trainRepository.getTrainBySouceAndDestination(source, destination)).thenReturn(new ArrayList<>());
		
		assertThrows(TrainNotFoundException.class, () -> {
			trainService.getTrainBySouceAndDestination(source, destination);
		});
	}
	
	@Test
	public void testGetTrainByTrainName() throws TrainNotFoundException {
		
		String trainName = "Train Name";
		List<Train> trainList = new ArrayList<>();
		trainList.add(new Train());
		when(trainRepository.getTrainByTrainName(trainName)).thenReturn(trainList);
		
		List<TrainOutputModel> result = trainService.getTrainByTrainName(trainName);
		
		assertNotNull(result);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGetTrainByTrainNameNotFound() {
		
		String trainName = "Train Name";
		when(trainRepository.getTrainByTrainName(trainName)).thenReturn(new ArrayList<>());
		
		assertThrows(TrainNotFoundException.class, () -> {
			trainService.getTrainByTrainName(trainName);
		});
	}
}
