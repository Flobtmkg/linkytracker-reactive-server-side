package flo.linky.open.dataserver.services;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flo.linky.open.dataserver.config.CurrentConfiguration;
import flo.linky.open.dataserver.dto.output.ServerConfigDTO;
import flo.linky.open.dataserver.repositories.ServerConfigRepository;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Service
public class ServerConfigService {
	
	private static Logger log = LoggerFactory.getLogger(ServerConfigService.class);
	
	@Autowired
	private ServerConfigRepository serverConfigRepository;
	
	
	@Autowired
	private CurrentConfiguration currentConfiguration;
	
	
	
	// Config auto-reload
	@PostConstruct
	private void updateConfig() {
		findServerConfig().doOnNext(config -> log.info("Additionnal server config updated. " + config.toString()))
    	.map(config -> currentConfiguration.pushConfig(config))
    	.repeat()
    	.delayElements(Duration.ofSeconds(30))
    	.subscribe();
	}
	
	
	public Mono<ServerConfigDTO> findServerConfig() {
		return serverConfigRepository.findFirstByOrderByIdDesc()
					.map(config -> 
						new ServerConfigDTO(
								currentConfiguration.getBackendVersion(),
								config.getDataFilterMilis(),
								config.getDefaultTimeWindow(),
								config.getDefaultTimeZoneOffset()
						)
					);
	}
	
	public void saveDataFilter(int dataFilter) {
		serverConfigRepository
			.findFirstByOrderByIdDesc()
			.map(config -> {
				config.setDataFilterMilis(dataFilter);
				return config;
				}
			)
			.map(config -> serverConfigRepository.save(config).subscribe()).subscribe();
	}
	
	public void saveDefaultTimeWindow(int defaultTimeWindow) {
		serverConfigRepository
			.findFirstByOrderByIdDesc()
			.map(config -> {
				config.setDefaultTimeWindow(defaultTimeWindow);
				return config;
				}
			)
			.map(config -> serverConfigRepository.save(config).subscribe()).subscribe();
	}
	
	public void saveDefaultTimeZoneOffset(int defaultTimeZoneOffset) {
		serverConfigRepository
			.findFirstByOrderByIdDesc()
			.map(config -> {
				config.setDefaultTimeZoneOffset(defaultTimeZoneOffset);
				return config;
				}
			)
			.map(config -> serverConfigRepository.save(config).subscribe()).subscribe();
	}
	
	
}
