package flo.linky.open.dataserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import flo.linky.open.dataserver.dto.output.ServerConfigDTO;
import flo.linky.open.dataserver.services.ServerConfigService;
import reactor.core.publisher.Mono;

// Authorized origin front App
@CrossOrigin(origins = "${allow-origins}")
@RestController
public class ServerConfigController {

	@Autowired
	private ServerConfigService serverConfigService;
	
	
	@GetMapping("/api/v1/config")
	public Mono<ServerConfigDTO> getConfig() {
		return serverConfigService.findServerConfig();
	}
	
	@PostMapping("/api/v1/config/dataFilter/{dataFilter}")
	public ResponseEntity<Void> postDataFilterConfig(@PathVariable Integer dataFilter) {
		if(null != dataFilter && dataFilter > 0) {
			serverConfigService.saveDataFilter(dataFilter);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/api/v1/config/defaultTimeWindow/{defaultTimeWindow}")
	public ResponseEntity<Void> postDefaultTimeWindowConfig(@PathVariable Integer defaultTimeWindow) {
		if(null != defaultTimeWindow && defaultTimeWindow > 1) {
			serverConfigService.saveDefaultTimeWindow(defaultTimeWindow);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}
	
}
