package flo.linky.open.dataserver.config;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import flo.linky.open.dataserver.dto.output.ServerConfigDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class CurrentConfiguration {
	
	private static Logger log = LoggerFactory.getLogger(CurrentConfiguration.class);

	@Value("${spring.profiles.active}")
	private String springProfil;
	@Value("${spring.r2dbc.url}")
	private String r2dbcPath;
	@Value("${spring.r2dbc.password}")
	private String secretR2dbcPassword;
	@Value("${spring.r2dbc.username}")
	private String secretR2dbcUsername;
	
	@Value("${server.port}")
	private String serverPort;
	@Value("${allow-origins}")
	private String allowOrigins;
	
	// Config automaticaly pushed by the ServerConfigService updateConfig() routine
	private ServerConfigDTO serverConfig;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void configLoadVerifier() throws IOException {
		
		log.info("------------------------------------------------------");
	    log.info("Application started with the following configuration :");
	    log.info("------------------------------------------------------");
	    Stream.of(CurrentConfiguration.class.getDeclaredFields())
	    	.filter(field -> field.getGenericType().equals(String.class))
	    	.filter(field -> Modifier.isPrivate(field.getModifiers()))
	    	.filter(field -> !field.getName().startsWith("secret"))
	    	.filter(field -> !Modifier.isStatic(field.getModifiers()))
	    	.filter(field -> !Mono.class.equals(field.getDeclaringClass()))
	    	.filter(field -> !Flux.class.equals(field.getDeclaringClass()))
	    	.forEach(field -> {
				try {
					log.info(field.getName() + " : " + (String)field.get(this));
				} catch (Exception e) {
					throw new RuntimeException(e); 
				}
			});
	    log.info("------------------------------------------------------");
	    	
	}
	
	public ServerConfigDTO pushConfig(ServerConfigDTO serverConfigDTO) {
		this.serverConfig = serverConfigDTO;
		return serverConfigDTO;
	}
	
	
	public ServerConfigDTO getServerConfig() {
		return serverConfig;
	}
	

	public String getServerPort() {
		return serverPort;
	}
	
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	
	public String getAllowOrigins() {
		return allowOrigins;
	}
	
	public void setAllowOrigins(String allowOrigins) {
		this.allowOrigins = allowOrigins;
	}
	
	public String getSpringProfil() {
		return springProfil;
	}
	
	public void setSpringProfil(String springProfil) {
		this.springProfil = springProfil;
	}
	
	public String getR2dbcPath() {
		return r2dbcPath;
	}
	
	public void setR2dbcPath(String r2dbcPath) {
		this.r2dbcPath = r2dbcPath;
	}

	public String getSecretR2dbcPassword() {
		return secretR2dbcPassword;
	}

	public String getSecretR2dbcUsername() {
		return secretR2dbcUsername;
	}

}
