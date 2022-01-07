package com.elhg.microservices.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.elhg.microservices.bean.CurrencyConversionBean;
import com.elhg.microservices.clients.CurrencyExchangeServiceFeign;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class); 

	
	@Autowired
	private CurrencyExchangeServiceFeign clientFeignExchange;
	
	//Make sure you start the services in this order 
	// (a) eureka-naming-server (b) zuul-api-gateway-server (c)currency-exchange-service (d)currency-conversion-service
	
	// http://localhost:8100/currency-converter-feign/from/EUR/to/INR/quantity/10000, 
	// http://localhost:8100/currency-converter/from/USD/to/INR/quantity/10, 
	// http://localhost:8765/currency-conversion-service/currency-converter-feign/from/USD/to/INR/quantity/10 


	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from , 
											      @PathVariable String to,
											      @PathVariable BigDecimal quantity ) {
		
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
				
		ResponseEntity<CurrencyConversionBean> responseEntity =  new RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		logger.info("{} ", response);
		
		return new CurrencyConversionBean(1L, from, to, 
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}


	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from , 
											      @PathVariable String to,
											      @PathVariable BigDecimal quantity ) {
		
		
		CurrencyConversionBean response = clientFeignExchange.retrieveExchangeValue(from, to);
		
		return new CurrencyConversionBean(1L, from, to, 
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}


}
