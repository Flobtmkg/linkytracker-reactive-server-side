package flo.linky.open.dataserver.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

@Service
public class TimeZoneConverter {
	
	public LocalDateTime convertClientDateToUTC(LocalDate naiveClientDate, String ZoneIdentifier) {
		
		return naiveClientDate.atStartOfDay(
				ZoneId.of(
						ZoneId.getAvailableZoneIds().stream()
							.filter(zoneId -> zoneId.equals(ZoneIdentifier))
							.findAny()
							.orElse(ZoneId.systemDefault().getId())
						)
				)
				.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
		
	}

}
