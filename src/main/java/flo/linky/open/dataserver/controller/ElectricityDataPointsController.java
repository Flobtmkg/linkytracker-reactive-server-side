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

import flo.linky.open.dataserver.config.CurrentConfiguration;
import flo.linky.open.dataserver.converters.ConsumptionConverter;
import flo.linky.open.dataserver.dto.inputs.SensorLedBlinkedDTO;
import flo.linky.open.dataserver.dto.output.DataGraphDTO;
import flo.linky.open.dataserver.dto.output.DeviceDTO;
import flo.linky.open.dataserver.services.DeviceService;
import flo.linky.open.dataserver.services.ElectricityConsumptionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Authorized origin front App
@CrossOrigin(origins = "${allow-origins}")
@RestController
public class ElectricityDataPointsController {
	
	private static Logger logger = LoggerFactory.getLogger(ElectricityDataPointsController.class);
	
	@Autowired
	private ElectricityConsumptionService ElectricityConsumptionService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private ConsumptionConverter consumptionConverterService;
	
	@Autowired
	private CurrentConfiguration currentConfiguration;
	
	
	
	private LocalDateTime cachedLastPostReceived = LocalDateTime.now();
	
	
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
		
		LocalDateTime inputTime = LocalDateTime.now();
		
		// If we are above the threshold tolerance we push the data otherwise we ignore it
		if(Duration.between(cachedLastPostReceived, inputTime).compareTo(Duration.ofMillis(currentConfiguration.getServerConfig().getDataFilter())) > 0) {
			cachedLastPostReceived = inputTime;
			// Add time from server
			consumptionDTO.setPointDate(inputTime);
			
			logger.info("Input detected from deviceId " + consumptionDTO.getDeviceId() + " value : " + consumptionDTO.getLedMilis());
			ElectricityConsumptionService.save(consumptionConverterService.convertSensorLedBlinkedDTOToElectricityConsumptionDataPointEntity(consumptionDTO)).subscribe();
		}
		// we return ok anyway
		return ResponseEntity.ok().build();
	}
	
	
	// Communication DataGraphDTO via SSE for last datas
	@GetMapping(value="/api/v1/datapoints/date", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<DataGraphDTO> getLastElectricityConsumptionDataPoints(
			@RequestParam("deviceId") String deviceId,
			@RequestParam("date") LocalDate date,
			ServerHttpResponse response) {
		
		logger.info("Lookup for device : " + deviceId + " date : " + date.toString());
		return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findBydeviceIdAndDate(deviceId, date))
				.repeat()
				.delayElements(Duration.ofMillis(30))
				.delaySubscription(Duration.ofMillis(1000));
	}

}
