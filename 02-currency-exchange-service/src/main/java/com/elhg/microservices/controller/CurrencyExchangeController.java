package com.elhg.microservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elhg.microservices.bean.ExchangeValue;
import com.elhg.microservices.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class); 

	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	//API GATEWAY
	//http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR
	
	// SIN GATEWAY
	//http://localhost:8000/currency-exchange/from/USD/to/INR
	
	//Make sure you start the services in this order 
	// (a) eureka-naming-server (b) zuul-api-gateway-server (c)currency-exchange-service (d)currency-conversion-service
	
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from , 
											   @PathVariable String to) {
		
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		
		logger.info("{}", exchangeValue);
		
		return exchangeValue;
	}
}
