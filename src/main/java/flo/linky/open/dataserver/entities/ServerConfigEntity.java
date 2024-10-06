package flo.linky.open.dataserver.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "SERVER_CONFIG")
public class ServerConfigEntity {

	@Id
	private Integer id;
	
	private int dataFilterMilis;
	private int defaultTimeWindow;
	private int defaultTimeZoneOffset;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDataFilterMilis() {
		return dataFilterMilis;
	}

	public void setDataFilterMilis(int dataFilterMilis) {
		this.dataFilterMilis = dataFilterMilis;
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
	
}
