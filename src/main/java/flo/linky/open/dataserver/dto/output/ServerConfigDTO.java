package flo.linky.open.dataserver.dto.output;

public class ServerConfigDTO {
	
	private int dataFilter;
	private int defaultTimeWindow;
	private int defaultTimeZoneOffset;
	private String backendVersion;
	
	public ServerConfigDTO(String backendVersion, int dataFilter, int defaultTimeWindow, int defaultTimeZoneOffset) {
		super();
		this.backendVersion = backendVersion;
		this.dataFilter = dataFilter;
		this.defaultTimeWindow = defaultTimeWindow;
		this.defaultTimeZoneOffset = defaultTimeZoneOffset;
	}
	
	public ServerConfigDTO() {
		super();
	}
	
	
	public int getDataFilter() {
		return dataFilter;
	}
	
	public void setDataFilter(int dataFilter) {
		this.dataFilter = dataFilter;
	}
	
	public int getDefaultTimeWindow() {
		return defaultTimeWindow;
	}

	public void setDefaultTimeWindow(int defaultTimeWindow) {
		this.defaultTimeWindow = defaultTimeWindow;
	}

	public int getDefaultTimeZoneOffset() {
		return defaultTimeZoneOffset;
	}

	public void setDefaultTimeZoneOffset(int defaultTimeZoneOffset) {
		this.defaultTimeZoneOffset = defaultTimeZoneOffset;
	}
	
	public String getBackendVersion() {
		return backendVersion;
	}

	public void setBackendVersion(String backendVersion) {
		this.backendVersion = backendVersion;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Config object : ");
		sb.append("backendVersion : " + this.backendVersion);
		sb.append("; dataFilter : " + this.dataFilter);
		sb.append("; defaultTimeWindow : " + this.defaultTimeWindow);
		sb.append("; defaultTimeZoneOffset : " + this.defaultTimeZoneOffset);
		return sb.toString();
	}
}
