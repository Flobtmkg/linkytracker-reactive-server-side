package flo.linky.open.dataserver.dto.inputs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SensorLedBlinkedDTO {
	
	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	private String deviceId;
	
	private int ledMilis;
	
	private LocalDateTime pointDate;

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
	
	public void setPointDate(String pointDate) {
		this.pointDate = LocalDateTime.from(FORMAT.parse(pointDate));
	}

	public int getLedMilis() {
		return ledMilis;
	}

	public void setLedMilis(int ledMilis) {
		this.ledMilis = ledMilis;
	}

}
