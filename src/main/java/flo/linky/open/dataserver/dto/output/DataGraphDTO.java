package flo.linky.open.dataserver.dto.output;

import java.math.BigDecimal;

public class DataGraphDTO {
	
	public DataGraphDTO(long x, BigDecimal y) {
		super();
		this.x = x;
		this.y = y;
	}
	private long x;
	private BigDecimal y;
	
	
	public BigDecimal getY() {
		return y;
	}
	public void setY(BigDecimal y) {
		this.y = y;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}

}
