package flo.linky.open.dataserver.dto.output;

import java.math.BigDecimal;

public class DataGraphDTO {
	
	public DataGraphDTO(String x, BigDecimal y) {
		super();
		this.x = x;
		this.y = y;
	}
	private String x;
	private BigDecimal y;
	
	
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public BigDecimal getY() {
		return y;
	}
	public void setY(BigDecimal y) {
		this.y = y;
	}

}
