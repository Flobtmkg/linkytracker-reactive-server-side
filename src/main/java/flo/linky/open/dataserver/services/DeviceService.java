package flo.linky.open.dataserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.dto.output.DeviceDTO;
import flo.linky.open.dataserver.repositories.ElectricityConsumptionRepository;
import reactor.core.publisher.Flux;

@Service
public class DeviceService {

	@Autowired
	private ElectricityConsumptionRepository electricityConsumptionRepository;
	
	
	public Flux<DeviceDTO> findAllDevices() {
		return electricityConsumptionRepository.findDevices()
				.map(strDevice -> new DeviceDTO(strDevice));
	}
	
}
