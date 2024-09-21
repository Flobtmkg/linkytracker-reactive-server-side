package flo.linky.open.dataserver.dto.output;

public class ServerConfigDTO {
	
	private int dataFilter;
	private int defaultTimeWindow;
	
	public ServerConfigDTO(int dataFilter, int defaultTimeWindow) {
		super();
		this.dataFilter = dataFilter;
		this.defaultTimeWindow = defaultTimeWindow;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Config object : ");
		sb.append("dataFilter : " + this.dataFilter);
		sb.append("; defaultTimeWindow : " + this.defaultTimeWindow);
		return sb.toString();
	}
}
