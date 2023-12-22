package flo.linky.open.dataserver.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.entities.ElectricityConsumptionDataPointEntity;
import flo.linky.open.dataserver.repositories.ElectricityConsumptionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ElectricityConsumptionService {

	@Autowired
	private ElectricityConsumptionRepository electricityConsumptionRepository;
	
	
	public Flux<ElectricityConsumptionDataPointEntity> findAll() {
		return electricityConsumptionRepository.findAll();
	}
	
	public Mono<ElectricityConsumptionDataPointEntity> findById(Integer id) {
		return electricityConsumptionRepository.findById(id);
	}
	
	public Mono<Void> delete(ElectricityConsumptionDataPointEntity entity) {
		return electricityConsumptionRepository.delete(entity);
	}
	
	public Mono<Void> deleteById(Integer id) {
		return electricityConsumptionRepository.deleteById(id);
	}
	
	public Mono<ElectricityConsumptionDataPointEntity> save(ElectricityConsumptionDataPointEntity entity) {
		return electricityConsumptionRepository.save(entity);
	}
	
	public Mono<Long> count() {
		return electricityConsumptionRepository.count();
	}
	
	public Flux<ElectricityConsumptionDataPointEntity> findBydeviceId(String deviceId) {
		return electricityConsumptionRepository.findByDeviceIdOrderByPointDateAsc(deviceId);
	}
	
	public Flux<ElectricityConsumptionDataPointEntity> findBydeviceIdAndDate(String deviceId, LocalDate date) {
		return electricityConsumptionRepository.findByDeviceIdAndPointDateBetweenOrderByPointDateAsc(deviceId, date, date.plusDays(1));
	}
	
	
}
