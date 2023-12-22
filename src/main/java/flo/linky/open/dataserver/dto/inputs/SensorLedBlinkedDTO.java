package flo.linky.open.dataserver.dto.inputs;

import java.time.LocalDateTime;

public class SensorLedBlinkedDTO {
	
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

	public int getLedMilis() {
		return ledMilis;
	}

	public void setLedMilis(int ledMilis) {
		this.ledMilis = ledMilis;
	}

}
