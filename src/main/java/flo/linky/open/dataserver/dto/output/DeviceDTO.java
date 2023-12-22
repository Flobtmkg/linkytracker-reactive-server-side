package flo.linky.open.dataserver.dto.output;

public class DeviceDTO {
	
	private static String LABEL = "Device ID : ";
	
	public DeviceDTO(String deviceId) {
		super();
		this.value = deviceId;
		this.label = LABEL + deviceId;
	}
	private String value;
	private String label;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
