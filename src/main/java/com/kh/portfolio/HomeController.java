package com.kh.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	private static final Logger logger 
		= LoggerFactory.getLogger(HomeController.class);
	
	//메인페이지
	@GetMapping("/")
	public String welcomePage() {
		
		return "index";
	}
	
	//코비드현황
	@GetMapping("/openapi/covidStatus")
	public String covidStatus() {
		return "/openapi/covid";
	}	
	
	//bootstrap
	@GetMapping("/bootstrap")
	public String bootstrap() {
		
		return "/test/bootstrap";
	}
	
	@GetMapping("/video/test")
	public String vodeo() {
		return "/video";
	}
	
	@GetMapping("/abc")
	public String hangle(@RequestParam("name") String name) {
		
		log.info(name);
		return "/video";
	}	
	
	@GetMapping("/ajax/rboard/list")
	public String ajax() {
		return "/test/ajaxTest";
	}
}




