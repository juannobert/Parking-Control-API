package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
	@Autowired
	ParkingSpotService service;
	
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
		if(service.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(service.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(service.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
		var parkingSpot = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
		parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parkingSpot));
		
	}
	
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Object> getParkingSpotBtId(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpotModel> optional = service.findById(id);
		if (!optional.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());

	}
	@PutMapping("/{id}")
	public ResponseEntity<Object> putParkingSpot(@PathVariable(value = "id") UUID id,@Valid @RequestBody ParkingSpotDto parkingSpotDto){
		Optional<ParkingSpotModel> optional = service.findById(id);
		if (!optional.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		var parkingSpotModel = optional.get();
		service.update(parkingSpotDto,parkingSpotModel);
		parkingSpotModel.setId(parkingSpotModel.getId());
		parkingSpotModel.setRegistrationDate(parkingSpotModel.getRegistrationDate());
		return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpotModel));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpotById(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpotModel> optional = service.findById(id);
		if (!optional.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		service.delete(optional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully");

	}

	
}
	

	
	
	
	

