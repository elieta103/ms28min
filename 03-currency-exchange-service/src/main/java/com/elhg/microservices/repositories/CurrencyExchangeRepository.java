package com.elhg.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elhg.microservices.bean.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

	CurrencyExchange findByFromAndTo(String from, String to);
}
