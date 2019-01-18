package com.mine.info.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mine.info.model.Technology;

@Service
public class TechnologyService {
	@Value ( "${tech.list.url}" )
	String url; 
	
	private Logger logger = LoggerFactory.getLogger(TechnologyService.class);
	
	@Autowired
	RestTemplate restTemplate; 
	
	public Technology getTechnologyById(int id) { 
		String uri = url+"/{id}"; 
		logger.info("url = "+uri); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", Long.toString(id));
	    
		//RestTemplate restTemplate = new RestTemplate(); 
		Technology tech = restTemplate.getForObject(uri, Technology.class, params); 
	
		logger.info("got tech, value is " + tech.getCategory() + tech.getTechnologyType());
		return tech; 
	}
}
