package com.kh.portfolio.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j  // log.info()
@RequestMapping("/openapi")
public class CovidController {

	final CovidSVC covidSVC;
	
	@Autowired
	public CovidController(CovidSVC covidSVC) {
		this.covidSVC = covidSVC;
	}
	
	@GetMapping("/covid")
	public String covidInfo(
			@RequestParam("startDt") String startDt,
			@RequestParam("endDt") String endDt
			) {
		
		return covidSVC.covidInfoJson(startDt,endDt);
	}
	@GetMapping("/covid/{startDt}/{endDt}")
	public String covidInfo2(
			@PathVariable("startDt") String startDt,
			@PathVariable("endDt") String endDt
			) {
		
		return covidSVC.covidInfoJson(startDt,endDt);
	}
}





