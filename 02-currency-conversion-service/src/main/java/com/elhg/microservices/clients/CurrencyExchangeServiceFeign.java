package com.elhg.microservices.clients;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.elhg.microservices.bean.CurrencyConversionBean;

//Sin Ribbon
//@FeignClient(name="currency-exchange-service", url="localhost:8000")

//Con Ribbon
//@FeignClient(name="currency-exchange-service")
//@RibbonClient(name="currency-exchange-service")


//Make sure you start the services in this order 
// (a) eureka-naming-server (b) zuul-api-gateway-server (c)currency-exchange-service (d)currency-conversion-service

// http://localhost:8100/currency-converter-feign/from/EUR/to/INR/quantity/10000, 
// http://localhost:8100/currency-converter/from/USD/to/INR/quantity/10, 
// http://localhost:8765/currency-conversion-service/currency-converter-feign/from/USD/to/INR/quantity/10 


@FeignClient(name="zuul-api-gateway-service")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceFeign {
	
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from , 
			   											@PathVariable("to") String to);

}
