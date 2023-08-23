package com.spring.railEase.util;

import com.spring.railEase.entity.Train;
import com.spring.railEase.model.TrainOutputModel;

public class MapTrainRow {
	public TrainOutputModel mapToTrainOutput(Train train)
	{
		TrainOutputModel trainOutputModel =new TrainOutputModel();
		trainOutputModel.setAc1fare(train.getAc1fare());
		trainOutputModel.setAc1Seats(train.getAc1Seats());
		trainOutputModel.setAc2fare(train.getAc2fare());
		trainOutputModel.setAc2seats(train.getAc2seats());
		trainOutputModel.setArrivalTime(train.getArrivalTime());
		trainOutputModel.setDepartureTime(train.getDepartureTime());
		trainOutputModel.setDestination(train.getDestination());
		trainOutputModel.setDuration(train.getDuration());
		trainOutputModel.setSlfare(train.getSlfare());
		trainOutputModel.setSlSeats(train.getSlSeats());
		trainOutputModel.setSource(train.getSource());
		trainOutputModel.setTrainName(train.getTrainName());
		trainOutputModel.setTrainNo(train.getTrainNo());
		return trainOutputModel;
	}
}
