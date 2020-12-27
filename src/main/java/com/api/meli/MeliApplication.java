package com.api.meli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan({"api","service"})
@EntityScan("model")
@EnableJpaRepositories("repository")
public class MeliApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MeliApplication.class, args);
	}
	
}
