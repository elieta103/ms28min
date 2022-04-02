package com.elhg.microservices.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elhg.microservices.bean.CurrencyExchange;
import com.elhg.microservices.repositories.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currentExchangeRepo;

	//http://localhost:8000/currency-exchange/from/EUR/to/INR
	//http://localhost:8765/currency-exchange/currency-exchange/from/EUR/to/INR

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
												  @PathVariable String to) {
		
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(0));
		
		logger.info("retrieveExchangeValue call with {} to {}", from, to);
		
		CurrencyExchange currencyExchange = currentExchangeRepo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("NO se encontró datos para "+from+ " to : "+to);
		}
		
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
		
		
		
		return currencyExchange;
		
	}
}
