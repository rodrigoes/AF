package com.projects.praticandoAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PraticandoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PraticandoApiApplication.class, args);
	}

}
