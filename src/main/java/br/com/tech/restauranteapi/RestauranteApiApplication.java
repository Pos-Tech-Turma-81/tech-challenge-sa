package br.com.tech.restauranteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestauranteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteApiApplication.class, args);
	}

}
