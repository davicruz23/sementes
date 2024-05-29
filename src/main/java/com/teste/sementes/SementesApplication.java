package com.teste.sementes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SementesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SementesApplication.class, args);
	}
	@Bean
	public ModelMapper mapper(){
		return new ModelMapper();
	}

}
