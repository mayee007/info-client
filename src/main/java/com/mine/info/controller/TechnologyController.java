package com.mine.info.controller;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.mine.info.model.Technology;

@Controller
public class TechnologyController {
	
	@Value ( "${tech.list.url}" )
	String url; 
	
	private Logger logger = LoggerFactory.getLogger(TechnologyController.class);
	
	@Autowired
	RestTemplate restTemplate; 
	
	@GetMapping("/listAllTechnology")
	String listAllTechnology(Map<String, Object> model) {
		logger.info("inside TechnologyController::listAllTechnology()");
		logger.info("url = "+url); 
		//RestTemplate restTemplate = new RestTemplate(); 
		Technology[] techs = restTemplate.getForObject(url, Technology[].class); 
		
		logger.info(techs.toString());
		for (Technology tech: techs) { 
			logger.info(tech.getTechnologyType() + " " + tech.getCategory());
		}
		model.put("techs", techs); 
		return "alltech";
	}

	@GetMapping("/listAllTechnology/{id}")
	String listTechnologyById(Map<String, Object> model, @PathVariable("id") long id) {
		
		String uri = url+"/{id}"; 
		logger.info("url = "+uri); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", Long.toString(id));
	    
		//RestTemplate restTemplate = new RestTemplate(); 
		Technology tech = restTemplate.getForObject(uri, Technology.class, params); 
		
		System.out.println(tech.getTechnologyType() + " " + tech.getCategory());
		
		model.put("tech", tech); 
		return "tech" ;
	}
	
	@DeleteMapping("/listAllTechnology/{id}")
	void deleteTechnologyById(@PathVariable("id") String id) {
		
		String uri = url+"/{id}"; 
		logger.info("inside delete technology() , id = " + id); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	    
	    logger.info("url = " + uri);
		restTemplate.delete(uri, params); 
		logger.info("after deletion of ID "+id);
	}
	
	@PostMapping("/listAllTechnology")
	String  addTechnology(Map<String, Object> model, @RequestParam("category") String category, 
			@RequestParam("technologyType") String technologyType) {
		
		logger.info("inside add technology() , values = " + category + " , "+technologyType); 
	    logger.info("url = " + url);
	    
	    //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
	    //builder.queryParam(category, category);
	    //builder.queryParam(technologyType, technologyType); 
	    
	    //HttpHeaders headers = new HttpHeaders();
	    //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    //HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
	            //new HttpEntity<>(params, headers);
	    
	    //Technology tech = new Technology(); 
	    //tech.setCategory(category);
	    //tech.setTechnologyType(technologyType);
	    //logger.info("tech id =" + tech.getTechnologyId());
	    //logger.info("going to call post for object");
		//Technology newTech = restTemplate.postForObject(url, tech, Technology.class); 
		//return newTech;
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("category", category);
        req_payload.put("technologyType", technologyType);

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        
        ResponseEntity<?> response = restTemplate.postForEntity(url, request, String.class);

        //Technology tech = (Technology) response.getBody();
        logger.info("tech = "+ response.toString() + ".......");
        logger.info("status = " + response.getStatusCode());
        logger.info("message = " + response.getBody() + " --------");
        //Technology tech = (Technology) response.getBody();
        model.put("tech", response.getBody()); 
		return "techAdded" ;
	}
}
