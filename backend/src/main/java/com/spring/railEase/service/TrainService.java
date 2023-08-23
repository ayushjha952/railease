package com.spring.railEase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Train;
import com.spring.railEase.model.TrainOutputModel;
import com.spring.railEase.service.exception.TrainNotFoundException;

@Service
public interface TrainService {
	
	public Train addTrain(Train train);
	
	public TrainOutputModel getTrainByTrainNo(String trainNo) throws TrainNotFoundException;
	
	public List<TrainOutputModel> getTrainBySouceAndDestination(String source, String destination) throws TrainNotFoundException;
	
	public List<TrainOutputModel> getTrainByTrainName(String trainName) throws TrainNotFoundException;
}
