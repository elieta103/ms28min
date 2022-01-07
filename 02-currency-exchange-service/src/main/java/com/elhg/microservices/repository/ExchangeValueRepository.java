package com.elhg.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elhg.microservices.bean.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	ExchangeValue findByFromAndTo(String from, String to);
	

}
