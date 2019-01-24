package com.mine.info.controller;

import java.util.Date;
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
import com.mine.info.model.Problem;
import com.mine.info.service.TechnologyService;

@Controller
public class ProblemController {
	
	@Value ( "${problem.list.url}" )
	String url; 
	
	@Autowired
	TechnologyService techService;
	
	private Logger logger = LoggerFactory.getLogger(TechnologyController.class);
	
	@Autowired
	RestTemplate restTemplate; 
	
	@GetMapping("/listAllProblem")
	String listAllInfo(Map<String, Object> model) {
		logger.info("inside ProblemController::listAllProblem()");
		logger.info("url = "+url); 
		//RestTemplate restTemplate = new RestTemplate(); 
		Problem[] problems = restTemplate.getForObject(url, Problem[].class); 
		
		//logger.info(problems.toString());
		//for (Problem problem: problems) { 
			//logger.info(problem.getProblem());
		//}
		model.put("problems", problems); 
		return "displayAllProblems";
	}

	@GetMapping("/listAllProblem/{id}")
	String listProblemById(Map<String, Object> model, @PathVariable("id") long id) {
		
		String uri = url+"/{id}"; 
		logger.info("url = "+uri); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", Long.toString(id));
	    
		//RestTemplate restTemplate = new RestTemplate(); 
	    Problem problem = restTemplate.getForObject(uri, Problem.class, params); 
		
		System.out.println(problem.getSolution());
		
		model.put("problem", problem); 
		return "singleProblem" ;
	}
	
	@DeleteMapping("/listAllProblem/{id}")
	String deleteProblemById(Map<String, Object> model, @PathVariable("id") String id) {
		
		String uri = url+"/{id}"; 
		logger.info("inside delete Problem() , id = " + id); 
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	     
	    logger.info("url = " + uri);
		restTemplate.delete(uri, params); 
		logger.info("after deletion of ID "+id);
		
		Problem[] problems = restTemplate.getForObject(url, Problem[].class); 
		
		/*logger.info(problems.toString());
		for (Problem problem: problems) { 
			logger.info(problem.getProblem());
		} */
		model.put("problems", problems); 
		return "displayAllProblems";
		
	}
	
	@PostMapping("/listAllProblem")
	String  addInfo(Map<String, Object> model, @RequestParam("techId") String techId,  
			@RequestParam("problem") String problem, 
			@RequestParam("reasonForProblem") String reasonForProblem,
			@RequestParam("solution") String solution) {
		
		logger.info("inside add Problem()"); 
	    logger.info("url = " + url);
	    
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("technology", techService.getTechnologyById(Integer.parseInt(techId)));
        req_payload.put("problem", problem);
        req_payload.put("reasonForProblem", reasonForProblem);
        req_payload.put("solution", solution); 
        req_payload.put("submitDate", new Date()); 
        req_payload.put("modifiedDate", new Date()); 

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        
        ResponseEntity<?> response = restTemplate.postForEntity(url, request, String.class);

        //Technology tech = (Technology) response.getBody();
        logger.info("Problem = "+ response.toString() + ".......");
        logger.info("status = " + response.getStatusCode());
        logger.info("message = " + response.getBody() + " --------");
        //Technology tech = (Technology) response.getBody();
        model.put("problem", response.getBody()); 
		return "problemAdded" ;
	}
}
