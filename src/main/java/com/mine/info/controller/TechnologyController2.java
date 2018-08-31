package com.mine.info.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.mine.info.model.Technology;

@Controller
@RequestMapping("listTechnology")
public class TechnologyController2 {
	
	@Value ( "${tech.list.url}" )
	String url; 
	
	private Logger logger = LoggerFactory.getLogger(TechnologyController2.class);
	
	RestTemplate restTemplate = new RestTemplate(); 
	
	String listAllTechnology(Map<String, Object> model) {
		logger.info("inside TechnologyController2::listAllTechnology()");
		RestTemplate restTemplate = new RestTemplate(); 
		Technology[] techs = restTemplate.getForObject(url, Technology[].class); 
		
		logger.info(techs.toString());
		for (Technology tech: techs) { 
			logger.info(tech.getTechnologyType() + " " + tech.getCategory());
		}
		model.put("techs", techs); 
		return "alltech";
	}

	@RequestMapping("{id}")
	String listTechnologyById(@RequestParam("id") String technologyId, Model map) {
		logger.info("inside TechnologyController2::listTechnologyById()");
		logger.info("id = " + technologyId);
		url = url+"/{technologyId}"; 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", technologyId);
	    
		RestTemplate restTemplate = new RestTemplate(); 
		Technology tech = restTemplate.getForObject(url, Technology.class, params); 
		
		logger.info(tech.getTechnologyType() + " " + tech.getCategory());
		
		map.addAttribute("tech", tech); 
		return "tech" ;
	}
	
	@RequestMapping("/deleteTechnology/{id}")
	void deleteTechnologyById(@PathVariable("id") String id) {
		
		url = url+"/"+id; 
		logger.info("inside delete technology() , id = " + id); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	    
	    logger.info("url = " + url);
	    		
		restTemplate.delete(url, params); 
		//return "tech" ;
	}
}
