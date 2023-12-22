package flo.linky.open.dataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class DataserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataserverApplication.class, args);
	}

}
