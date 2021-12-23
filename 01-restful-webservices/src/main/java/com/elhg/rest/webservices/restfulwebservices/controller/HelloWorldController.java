package com.elhg.rest.webservices.restfulwebservices.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elhg.rest.webservices.restfulwebservices.bean.HelloWorldBean;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	

	@GetMapping(path="/hello-world")
	public String helloWorld () {
		return "Hello World";
	}

	/*   //OPCION 1   Add header : Accept-Language fr ó nl
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized (
			   		@RequestHeader(name="Accept-Language", required = false)Locale locale ) {		
		return messageSource
				.getMessage("good.morning.message", null, "Default Message",  locale);
	}*/
	
	   //OPCION 2   Add header : Accept-Language fr ó nl
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized () {		
		return messageSource
				.getMessage("good.morning.message", null, "Default Message",  LocaleContextHolder.getLocale());
	}

	
	//Sin getMessage, no funciona
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean () {
		return new HelloWorldBean("Hello World from Bean");
	}

	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable (@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s from Bean", name));
	}

}
