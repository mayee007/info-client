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
import com.mine.info.model.Info;
import com.mine.info.model.Technology;
import com.mine.info.service.TechnologyService;

@Controller
public class InfoController {
	
	@Value ( "${info.list.url}" )
	String url; 
	
	@Autowired
	TechnologyService techService;
	
	private Logger logger = LoggerFactory.getLogger(InfoController.class);
	
	@Autowired
	RestTemplate restTemplate; 
	
	@GetMapping("/listAllInfo")
	String listAllInfo(Map<String, Object> model) {
		logger.info("inside InfoController::listAllInfo()");
		logger.info("url = "+url); 
		//RestTemplate restTemplate = new RestTemplate(); 
		Info[] infos = restTemplate.getForObject(url, Info[].class); 
		
		/* logger.info(infos.toString());
		for (Info info: infos) { 
			logger.info(info.getSubject());
		} */
		model.put("infos", infos); 
		return "displayAddInfos";
	}

	@GetMapping("/listAllInfo/{id}")
	String listInfoById(Map<String, Object> model, @PathVariable("id") long id) {
		
		String uri = url+"/{id}"; 
		logger.info("url = "+uri); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", Long.toString(id));
	    
		//RestTemplate restTemplate = new RestTemplate(); 
		Info info = restTemplate.getForObject(uri, Info.class, params); 
		
		System.out.println(info.getSubject());
		
		model.put("info", info); 
		return "singleInfo" ;
	}
	
	@DeleteMapping("/listAllInfo/{id}")
	String deleteInfoById(Map<String, Object> model, @PathVariable("id") String id) {
		
		String uri = url+"/{id}"; 
		logger.info("inside delete info() , id = " + id); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	     
	    logger.info("url = " + uri);
		restTemplate.delete(uri, params); 
		logger.info("after deletion of ID "+id);
		
		logger.info("inside InfoController::listAllInfo()");
		logger.info("url = "+url); 
		
		Info[] infos = restTemplate.getForObject(url, Info[].class); 
		
		/* logger.info(infos.toString());
		for (Info info: infos) { 
			logger.info(info.getSubject());
		} */
		model.put("infos", infos); 
		return "displayAddInfos";
		
	}
	
	@PostMapping("/listAllInfo")
	String  addInfo(Map<String, Object> model, @RequestParam("techId") String techId, 
			@RequestParam("subject") String subject, 
			@RequestParam("description") String description) {
		
		logger.info("inside add info() , values = " + techId);
		logger.info("subject" +subject);
		logger.info("description " + description);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Technology tech = techService.getTechnologyById(Integer.parseInt(techId)); 
        Map req_payload = new HashMap();
        //req_payload.put("category", tech.getCategory());
        req_payload.put("technology", tech);
        req_payload.put("subject", subject); 
        req_payload.put("description", description); 

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        
        ResponseEntity<?> response = restTemplate.postForEntity(url, request, String.class);

        logger.info("info = "+ response.toString() + ".......");
        logger.info("status = " + response.getStatusCode());
        logger.info("message = " + response.getBody() + " --------");
        
        model.put("info", response.getBody()); 
        model.put("msg" , "some text");
        logger.info("going to call html page"); 
		return "infoAdded" ;
	}
}
