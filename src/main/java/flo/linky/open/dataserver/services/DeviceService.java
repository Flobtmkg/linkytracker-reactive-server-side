package flo.linky.open.dataserver.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.dto.output.DeviceDTO;
import flo.linky.open.dataserver.repositories.ElectricityConsumptionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeviceService {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private ElectricityConsumptionRepository electricityConsumptionRepository;
	
	
	public Flux<DeviceDTO> findAllDevices() {
		return electricityConsumptionRepository.findDevices()
				.map(strDevice -> new DeviceDTO(strDevice));
	}
	
	public Mono<String> findLastData(String deviceId) {
		return electricityConsumptionRepository.findLastDataByDeviceId(deviceId)
				.map(date -> DATE_FORMAT.format(date))
				.single();
	}
	
	
	
}
