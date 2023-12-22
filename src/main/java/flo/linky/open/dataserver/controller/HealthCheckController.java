package flo.linky.open.dataserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
	private static Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

	
	@GetMapping("/api/v1/check")
	public String electricityConsumptionDataPoints() {
		
		logger.info("check OK!");

		return "check OK!";
	}

}
