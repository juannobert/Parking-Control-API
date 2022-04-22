package com.api.parkingcontrol.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;

@Service
public class ParkingSpotService {
	@Autowired
	ParkingSpotRepository repository;

	
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpot) {
		return repository.save(parkingSpot);
	}
}
