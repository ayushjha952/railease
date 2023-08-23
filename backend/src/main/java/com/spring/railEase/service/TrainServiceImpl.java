package com.spring.railEase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.railEase.entity.Train;
import com.spring.railEase.model.TrainOutputModel;
import com.spring.railEase.repository.TrainRepository;
import com.spring.railEase.service.exception.TrainNotFoundException;
import com.spring.railEase.util.MapTrainRow;

@Service
public class TrainServiceImpl implements TrainService{
	@Autowired
	TrainRepository trainRepository;
	
	public Train addTrain(Train train) {
		train = trainRepository.save(train);
		return train;
	}
	
	public TrainOutputModel getTrainByTrainNo(String trainNo) throws TrainNotFoundException {
		Train train = trainRepository.findById(trainNo).orElse(null);
		if(train==null)
			throw new TrainNotFoundException("Train is not avaiable......");
		TrainOutputModel trainOutputModel= new MapTrainRow().mapToTrainOutput(train);
		return trainOutputModel;
	}
	
	public List<TrainOutputModel> getTrainBySouceAndDestination(String source, String destination) throws TrainNotFoundException{
		List<Train> trainList = trainRepository.getTrainBySouceAndDestination(source,destination);
		if(trainList.isEmpty())
			throw new TrainNotFoundException("Train is not avaiable......");
		List<TrainOutputModel> trainOutputModelList= new ArrayList<TrainOutputModel>();
		for(Train train:trainList) {
			trainOutputModelList.add(new MapTrainRow().mapToTrainOutput(train));
		}
		return trainOutputModelList;
	}
	
	public List<TrainOutputModel> getTrainByTrainName(String trainName) throws TrainNotFoundException{
		List<Train> trainList = trainRepository.getTrainByTrainName(trainName);
		if(trainList.isEmpty())
			throw new TrainNotFoundException("Train is not avaiable......");
		List<TrainOutputModel> trainOutputModelList= new ArrayList<TrainOutputModel>();
		for(Train train:trainList) {
			trainOutputModelList.add(new MapTrainRow().mapToTrainOutput(train));
		}
		return trainOutputModelList;
	}
	
}
