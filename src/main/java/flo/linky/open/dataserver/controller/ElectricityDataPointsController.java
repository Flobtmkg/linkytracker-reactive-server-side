package flo.linky.open.dataserver.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import flo.linky.open.dataserver.converters.ConsumptionConverter;
import flo.linky.open.dataserver.dto.inputs.SensorLedBlinkedDTO;
import flo.linky.open.dataserver.dto.output.DataGraphDTO;
import flo.linky.open.dataserver.dto.output.DeviceDTO;
import flo.linky.open.dataserver.services.DeviceService;
import flo.linky.open.dataserver.services.ElectricityConsumptionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Authorized origin front App
@CrossOrigin(origins = "http://localhost:19006")
@RestController
public class ElectricityDataPointsController {
	
	private static Logger logger = LoggerFactory.getLogger(ElectricityDataPointsController.class);

	@Autowired
	private ElectricityConsumptionService ElectricityConsumptionService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private ConsumptionConverter consumptionConverterService;
	
	
	@GetMapping("/api/v1/device/recent/activity")
	public Mono<Boolean> getRecentActivity(@RequestParam("deviceId") String deviceId) {
		if(null != deviceId && !"".equals(deviceId)) {
			return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findBydeviceIdAndDate(deviceId, LocalDate.now()))
					.hasElements();
		}
		return Mono.empty();
	}
	
	
	@GetMapping("/api/v1/devices")
	public Flux<DeviceDTO> getDevices() {
		return deviceService.findAllDevices();
	}
	
	
	@PostMapping("/api/v1/datapoints")
	public ResponseEntity<Void> postElectricityConsumptionDataPoints(@RequestBody SensorLedBlinkedDTO consumptionDTO) {
		
		// Add time from server
		consumptionDTO.setPointDate(LocalDateTime.now());
		
		logger.info("Input detected from deviceId " + consumptionDTO.getDeviceId() + " value : " + consumptionDTO.getLedMilis());
		ElectricityConsumptionService.save(consumptionConverterService.convertSensorLedBlinkedDTOToElectricityConsumptionDataPointEntity(consumptionDTO)).subscribe();
		
		return ResponseEntity.ok().build();
	}
	
	
	// Communication DataGraphDTO via SSE
	@GetMapping(value="/api/v1/datapoints/date", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<DataGraphDTO> getLastElectricityConsumptionDataPoints(@RequestParam("deviceId") String deviceId, @RequestParam("date") LocalDate date, ServerHttpResponse response) {
		logger.info("Contact with device : " + deviceId + " and date : " + date.toString());
		return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findBydeviceIdAndDate(deviceId, date))
				.repeat()
				.delayElements(Duration.ofMillis(20))
				.delaySubscription(Duration.ofMillis(1000));
	}

}
