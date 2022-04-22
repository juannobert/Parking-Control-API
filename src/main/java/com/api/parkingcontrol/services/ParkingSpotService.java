package com.api.parkingcontrol.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return repository.existsByApartmentAndBlock(apartment, block);
	}
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return repository.existsByLicensePlateCar(licensePlateCar);
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return repository.existsByParkingSpotNumber(parkingSpotNumber);
	}
	public List<ParkingSpotModel> findAll(){
		return repository.findAll();
	}
	public Optional<ParkingSpotModel> findById(UUID id){
		return repository.findById(id);
	}
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		repository.delete(parkingSpotModel);
	}
}
