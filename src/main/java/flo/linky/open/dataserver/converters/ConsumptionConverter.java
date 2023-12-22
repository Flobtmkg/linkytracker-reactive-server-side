package flo.linky.open.dataserver.converters;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.dto.inputs.SensorLedBlinkedDTO;
import flo.linky.open.dataserver.dto.output.DataGraphDTO;
import flo.linky.open.dataserver.entities.ElectricityConsumptionDataPointEntity;
import reactor.core.publisher.Flux;

@Service
public class ConsumptionConverter {
	
	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH'h'mm ss's' SSS'ms'");
	private static final float KWH_MINUTE_BASE = 60;
	
	
	public ElectricityConsumptionDataPointEntity convertSensorLedBlinkedDTOToElectricityConsumptionDataPointEntity(SensorLedBlinkedDTO sensorLedBlinkedDTO) {
		
		ElectricityConsumptionDataPointEntity electricityConsumptionDataPointEntity = new ElectricityConsumptionDataPointEntity();
		
		electricityConsumptionDataPointEntity.setDeviceId(sensorLedBlinkedDTO.getDeviceId());
		electricityConsumptionDataPointEntity.setPointDate(sensorLedBlinkedDTO.getPointDate());
		electricityConsumptionDataPointEntity.setLedMilis(sensorLedBlinkedDTO.getLedMilis());
		
		return electricityConsumptionDataPointEntity;
	}
	
	
	public Flux<DataGraphDTO> convertElectricityConsumptionDataPointEntitiesToDataGraphDTO(Flux<ElectricityConsumptionDataPointEntity> datas) {
		
		return datas.map(data -> 
			new DataGraphDTO(
				FORMAT.format(data.getPointDate()),
				new BigDecimal(
					data.getLedMilis() !=0 ? KWH_MINUTE_BASE/((float)data.getLedMilis()) : 0,
					new MathContext(4, RoundingMode.HALF_EVEN)
				)
			)
		);
	}
	
	
}
