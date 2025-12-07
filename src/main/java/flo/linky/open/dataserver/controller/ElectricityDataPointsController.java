package flo.linky.open.dataserver.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import flo.linky.open.dataserver.utils.TimeZoneConverter;
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
	
	@Autowired
	private TimeZoneConverter timeZoneConverter;
	
	
	// We keep the last received data point in cache so we can filter false positives with a time threshold
	// TODO: Should work per device not as a global parameter!
	private LocalDateTime cachedLastPostReceived = LocalDateTime.now();
	
	
	@GetMapping("/api/v1/device/recent/activity")
	public Mono<String> getRecentActivity(@RequestParam("deviceId") String deviceId) {
		if(null != deviceId && !"".equals(deviceId)) {
			return deviceService.findLastData(deviceId);
		}
		return Mono.empty();
	}
	
	
	@GetMapping(value="/api/v1/devices", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<DeviceDTO> getDevices() {
		return deviceService.findAllDevices()
				.repeat()
				.delayElements(Duration.ofMillis(500))
				.delaySubscription(Duration.ofMillis(2000));
	}
	
	
	@PostMapping("/api/v1/datapoints")
	public ResponseEntity<Void> postElectricityConsumptionDataPoints(@RequestBody SensorLedBlinkedDTO consumptionDTO) {
		
		// time is saved as UTC
		LocalDateTime inputTime = LocalDateTime.now(ZoneOffset.UTC);
		
		// If we are above the threshold tolerance we push the data otherwise we ignore it
		if(Duration.between(cachedLastPostReceived, inputTime).compareTo(Duration.ofMillis(currentConfiguration.getServerConfig().getDataFilter())) > 0) {
			cachedLastPostReceived = inputTime;
			// Add UTC time
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
			@RequestParam("zone") String zone) {
		
		
		LocalDateTime correctedDate = timeZoneConverter.convertClientDateToUTC(date, zone);
		
		logger.info("Lookup for device : " + deviceId + " date : " + date.toString());
		return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findBydeviceIdAndDate(deviceId, correctedDate))
				.repeat()
				.delayElements(Duration.ofMillis(30))
				.delaySubscription(Duration.ofMillis(1000));
	}
	
	
	// Simple list of data per day in one JSON
	@GetMapping(value="/api/v2/datapoints/date", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<List<DataGraphDTO>> getElectricityConsumptionDataPointsByDate(
			@RequestParam("deviceId") String deviceId,
			@RequestParam("date") LocalDate date,
			@RequestParam("zone") String zone) {
		
		LocalDateTime correctedDate = timeZoneConverter.convertClientDateToUTC(date, zone);
		
		logger.info("Lookup for device : " + deviceId + " date : " + date.toString());
		return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findBydeviceIdAndDate(deviceId, correctedDate))
				.collectList();
	}
	
	// Communication of the last DataGraphDTO via SSE for last datas
	@GetMapping(value="/api/v2/datapoints/last/date", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<DataGraphDTO> getLastElectricityConsumptionDataPoint(
			@RequestParam("deviceId") String deviceId,
			@RequestParam("date") LocalDate date,
			@RequestParam("zone") String zone) {
		
		LocalDateTime correctedDate = timeZoneConverter.convertClientDateToUTC(date, zone);
		
		logger.info("Lookup for device : " + deviceId + " date : " + date.toString());
		return consumptionConverterService.convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(ElectricityConsumptionService.findLastBydeviceIdAndDate(deviceId, correctedDate))
				.repeat()
				.delayElements(Duration.ofMillis(250))
				.delaySubscription(Duration.ofMillis(1000));
	}
}
