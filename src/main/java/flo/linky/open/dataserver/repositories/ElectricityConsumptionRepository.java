package flo.linky.open.dataserver.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import flo.linky.open.dataserver.entities.ElectricityConsumptionDataPointEntity;
import reactor.core.publisher.Flux;

@Repository
public interface ElectricityConsumptionRepository extends R2dbcRepository<ElectricityConsumptionDataPointEntity, Integer> {
	
	
	public Flux<ElectricityConsumptionDataPointEntity> findByDeviceIdOrderByPointDateAsc(String deviceId);
	
	public Flux<ElectricityConsumptionDataPointEntity> findByDeviceIdAndPointDateBetweenOrderByPointDateAsc(String deviceId, LocalDate from, LocalDate to);

	@Query("SELECT POINT_DATE FROM ELECTRICITY_CONSUMPTION_DATA_POINT_ENTITY WHERE DEVICE_ID=:device ORDER BY POINT_DATE DESC LIMIT 1")
	public Flux<LocalDateTime> findLastDataByDeviceId(@Param("device")String deviceId);
	
	@Query("SELECT DISTINCT(DEVICE_ID) FROM ELECTRICITY_CONSUMPTION_DATA_POINT_ENTITY")
	public Flux<String> findDevices();
}
