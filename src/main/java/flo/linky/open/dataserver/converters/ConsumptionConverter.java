package flo.linky.open.dataserver.converters;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.dto.inputs.SensorLedBlinkedDTO;
import flo.linky.open.dataserver.dto.output.DataGraphDTO;
import flo.linky.open.dataserver.entities.ElectricityConsumptionDataPointEntity;
import reactor.core.publisher.Flux;

@Service
public class ConsumptionConverter {
	
	private static final float CONVERT_WH_ms_TO_INSTANT_AVERAGE_WATT = 3600000;
	
	
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
					data.getPointDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
				new BigDecimal(
					data.getLedMilis() !=0 ? (1/((float)data.getLedMilis())*CONVERT_WH_ms_TO_INSTANT_AVERAGE_WATT) : 0,
					new MathContext(4, RoundingMode.HALF_EVEN)
				)
			)
		);
	}
	
	
}
