package com.spring.railEase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.railEase.model.TrainOutputModel;
import com.spring.railEase.service.TrainService;
import com.spring.railEase.service.exception.TrainNotFoundException;

@RestController
public class TrainController {

	Logger logger =LoggerFactory.getLogger(TrainController.class);
//	@Autowired
//	TrainRepository trainRepository;
	@Autowired
	TrainService trainService;
	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PostMapping(value="/addtrain")
//	public Train addTrain(@RequestBody Train train) {
//		logger.info("addTrain method started");
//		train = trainRepository.save(train);
//		logger.info("Train: {}",train);
//		logger.info("addTrain method ended");
//		return train;
//	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/searchtrainbytrainno/{trainNo}")
	public ResponseEntity<TrainOutputModel> getTrainByTrainNo(@PathVariable("trainNo") String trainNo) throws TrainNotFoundException {
		logger.info("getTrainByTrainNo() method started.");
		TrainOutputModel trainOutputModel = trainService.getTrainByTrainNo(trainNo);
		logger.info("Train : {}",trainOutputModel);
		logger.info("getTrainByTrainNo() method ended.");
		return new ResponseEntity<TrainOutputModel>(trainOutputModel,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/searchtrainbysourceanddestination/{source}/{destination}")
	public ResponseEntity<List<TrainOutputModel>> getTrainBySouceAndDestination(@PathVariable("source") String source,@PathVariable("destination") String destination) throws TrainNotFoundException{
		logger.info("getTrainBySouceAndDestination() method started.");
		List<TrainOutputModel> trainOutputModelList = trainService.getTrainBySouceAndDestination(source,destination);
		logger.info("Train : {}",trainOutputModelList);
		logger.info("getTrainBySouceAndDestination() method ended.");
		return new ResponseEntity<List<TrainOutputModel>>(trainOutputModelList,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/searchtrainbytrainname/{trainName}")
	public ResponseEntity<List<TrainOutputModel>> getTrainByTrainName(@PathVariable("trainName") String trainName) throws TrainNotFoundException{
		logger.info("getTrainByTrainName() method started.");
		List<TrainOutputModel> trainOutputModelList = trainService.getTrainByTrainName(trainName);
		logger.info("Train : {}",trainOutputModelList);
		logger.info("getTrainByTrainName() method ended.");
		return new ResponseEntity<List<TrainOutputModel>>(trainOutputModelList,HttpStatus.OK);
	}
}
