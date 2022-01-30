package com.elhg.microservices.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.elhg.microservices.bean.CurrencyConversion;


//@FeignClient(name="currency-exchange", url="localhost:8000")  //Sin Load balance
@FeignClient(name="currency-exchange")   //Con Load Balancer
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);

}
