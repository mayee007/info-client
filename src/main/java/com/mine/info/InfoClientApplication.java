package com.mine.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.mine.info.model.Technology;

@SpringBootApplication
public class InfoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoClientApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}
