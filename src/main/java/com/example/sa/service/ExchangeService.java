package com.example.sa.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.repository.ExchangeRateRepository;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class ExchangeService {
	
	private ExchangeRateRepository exchangeRateRepository;
	
	private final MeterRegistry meterRegistry;
	
	@Autowired
	public ExchangeService(ExchangeRateRepository exchangeRateRepository , MeterRegistry meterRegistry) {
		this.exchangeRateRepository = exchangeRateRepository;
		this.meterRegistry = meterRegistry;
	}
	
	public ExchangeResponse getExchange(Currency from, Currency to, Double fromAmount) {
		log.info("Calling getExchange from {} to {} and fromAmount {}",from, to, fromAmount);
		ExchangeRate exchangeRate = exchangeRateRepository.findByBaseCurrencyAndToCurrency(from, to)
						.orElseThrow(() ->new NoSuchElementException(String.format("No conversion from %s to %s exists!",from, to)));
		meterRegistry.counter(
		 "vttanhua.exchangeService.convert_"+from.toString()+"_"+to.toString(),
		 Tags.of("outcome", "success"))
		 .increment();	
		return ExchangeResponse.builder()
							   .from(from)
							   .to(to)
							   .toAmount(fromAmount * exchangeRate.getRate())
							   .exchangeRate(exchangeRate.getRate())
							   .timestamp(exchangeRate.getTimestamp())
							   .build();
	}

}
