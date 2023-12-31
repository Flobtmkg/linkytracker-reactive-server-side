package flo.linky.open.dataserver.config;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class CurrentConfigurationLogger {
	
	private static Logger log = LoggerFactory.getLogger(CurrentConfigurationLogger.class);

	@Value("${spring.profiles.active}")
	private String springProfil;
	@Value("${spring.r2dbc.url}")
	private String r2dbcPath;
	@Value("${server.port}")
	private String serverPort;
	@Value("${allow-origins}")
	private String allowOrigins;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("------------------------------------------------------");
	    log.info("Application started with the following configuration :");
	    log.info("------------------------------------------------------");
	    Stream.of(CurrentConfigurationLogger.class.getDeclaredFields())
	    	.filter(field -> field.getGenericType().equals(String.class))
	    	.filter(field -> Modifier.isPrivate(field.getModifiers()))
	    	.filter(field -> !Modifier.isStatic(field.getModifiers()))
	    	.forEach(field -> {
				try {
					log.info(field.getName() + " : " + (String)field.get(this));
				} catch (Exception e) {
					throw new RuntimeException(e); 
				}
			});
	   
	    log.info("------------------------------------------------------");
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
}
