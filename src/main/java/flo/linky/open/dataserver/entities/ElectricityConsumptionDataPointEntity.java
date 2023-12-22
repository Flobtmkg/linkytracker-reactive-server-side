package flo.linky.open.dataserver.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "ELECTRICITY_CONSUMPTION_DATA_POINT_ENTITY")
public class ElectricityConsumptionDataPointEntity {

	@Id
	private Integer id;
	
	private String deviceId;
	
	private int ledMilis;
	
	private LocalDateTime pointDate;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public LocalDateTime getPointDate() {
		return pointDate;
	}

	public void setPointDate(LocalDateTime pointDate) {
		this.pointDate = pointDate;
	}

	public int getLedMilis() {
		return ledMilis;
	}

	public void setLedMilis(int ledMilis) {
		this.ledMilis = ledMilis;
	}
	
}
