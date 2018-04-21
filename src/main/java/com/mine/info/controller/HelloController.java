package com.mine.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping("/")
	String hello() {
		System.out.println("inside hello"); 
		return "index"; 
	}
}
