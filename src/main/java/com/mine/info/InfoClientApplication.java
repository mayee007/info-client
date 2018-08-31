package com.mine.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.mine.info.model.Technology;

@SpringBootApplication
public class InfoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoClientApplication.class, args);
		
		RestTemplate restTemplate = new RestTemplate();
		  Technology tech = restTemplate
		    .getForObject("http://info-app-api:8080/info/technology/{id}", Technology.class,2);
		  System.out.println("tech"+tech.getCategory());
		  System.out.println("tech"+tech.getTechnologyType());
		  
	}
}
