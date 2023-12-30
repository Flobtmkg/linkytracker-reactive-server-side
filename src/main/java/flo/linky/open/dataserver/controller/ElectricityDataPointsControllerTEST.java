package flo.linky.open.dataserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import flo.linky.open.dataserver.converters.ConsumptionConverter;
import flo.linky.open.dataserver.dto.inputs.SensorLedBlinkedDTO;
import flo.linky.open.dataserver.services.ElectricityConsumptionService;

// Authorized origin front App
@CrossOrigin(origins = "${allow-origins}")
@RestController
@Profile("test")
public class ElectricityDataPointsControllerTEST {
	
	private static Logger logger = LoggerFactory.getLogger(ElectricityDataPointsControllerTEST.class);

	@Autowired
	private ElectricityConsumptionService ElectricityConsumptionService;
	
	@Autowired
	private ConsumptionConverter consumptionConverterService;
	
	
	// Endpoint to test real time data insertion on a custom date
	// Precision up to to seconds
	/*
	{
		"deviceId": "18faa0dd7a927906cb3e38fd4ff6899fbe468e6841caac211b8d2cebe0ef4a44",
		"ledMilis": 1300,
		"pointDate": "2023-04-01 03:11:48"
	}*/
	@PostMapping("/api/v1/datapoint/test")
	public ResponseEntity<Void> postElectricityConsumptionDataPointsTest(@RequestBody SensorLedBlinkedDTO consumptionDTO) {
		
		logger.info("Input detected from deviceId " + consumptionDTO.getDeviceId() + " value : " + consumptionDTO.getLedMilis());
		ElectricityConsumptionService.save(consumptionConverterService.convertSensorLedBlinkedDTOToElectricityConsumptionDataPointEntity(consumptionDTO)).subscribe();
		
		return ResponseEntity.ok().build();
	}
	
}
