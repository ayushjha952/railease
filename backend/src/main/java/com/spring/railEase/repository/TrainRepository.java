package com.spring.railEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.railEase.entity.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train,String>{
	@Query(value="select t from Train t where t.source=?1 and t.destination=?2")
    public List<Train> getTrainBySouceAndDestination(String source, String destination);
	
	@Query(value="select t from Train t where t.trainName=?1")
	public List<Train> getTrainByTrainName(String trainName);
}

