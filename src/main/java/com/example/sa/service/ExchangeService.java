package com.example.sa.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.repository.ExchangeRateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class ExchangeService {
	
	private ExchangeRateRepository exchangeRateRepository;
	
	
	@Autowired
	public ExchangeService(ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}
	
	public ExchangeResponse getExchange(Currency from, Currency to, Double fromAmount) {
		ExchangeRate exchangeRate = exchangeRateRepository.findByBaseCurrencyAndToCurrency(from, to)
						.orElseThrow(() ->new NoSuchElementException(String.format("No conversion from %s to %s exists!",from, to)));
		return ExchangeResponse.builder()
							   .from(from)
							   .to(to)
							   .toAmount(fromAmount * exchangeRate.getRate())
							   .exchangeRate(exchangeRate.getRate())
							   .timestamp(exchangeRate.getTimestamp())
							   .build();
	}

}
