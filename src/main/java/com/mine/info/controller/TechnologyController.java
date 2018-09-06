package com.mine.info.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.mine.info.model.Technology;

@Controller
public class TechnologyController {
	
	@Value ( "${tech.list.url}" )
	String url; 
	
	private Logger logger = LoggerFactory.getLogger(TechnologyController.class);
	
	@Autowired
	RestTemplate restTemplate; // = new RestTemplate(); 
	
	@RequestMapping("/listAllTechnology")
	String listAllTechnology(Map<String, Object> model) {
		logger.info("inside TechnologyController2::listAllTechnology()");
		//RestTemplate restTemplate = new RestTemplate(); 
		Technology[] techs = restTemplate.getForObject(url, Technology[].class); 
		
		logger.info(techs.toString());
		for (Technology tech: techs) { 
			logger.info(tech.getTechnologyType() + " " + tech.getCategory());
		}
		model.put("techs", techs); 
		return "alltech";
	}

	@RequestMapping("/listAllTechnology/{id}")
	String listTechnologyById(Map<String, Object> model, @PathVariable("id") long id) {
		
		url = url+"/{id}"; 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", Long.toString(id));
	    
		//RestTemplate restTemplate = new RestTemplate(); 
		Technology tech = restTemplate.getForObject(url, Technology.class, params); 
		
		System.out.println(tech.getTechnologyType() + " " + tech.getCategory());
		
		model.put("tech", tech); 
		return "tech" ;
	}
	
	@RequestMapping("/deleteTechnology/{id}")
	void deleteTechnologyById(@PathVariable("id") String id) {
		
		url = url+"/"+id; 
		logger.info("inside delete technology() , id = " + id); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	    
	    logger.info("url = " + url);
	    
	    //RestTemplate restTemplate = new RestTemplate(); 
		restTemplate.delete(url, params); 
		//return "tech" ;
	}
}
