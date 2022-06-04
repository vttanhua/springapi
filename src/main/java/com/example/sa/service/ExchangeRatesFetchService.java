package com.example.sa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.client.ExchangeRatesClient;
import com.example.sa.dto.ExchangeRatesResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.repository.ExchangeRateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class ExchangeRatesFetchService {
	
	@Value("${springapi.exchangeRatesAPIKey}")
	private String exchangeRatesAPIKey;
	
	public ExchangeRatesClient exchangeRatesClient;
	
	public ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	public ExchangeRatesFetchService(ExchangeRatesClient exchangeRatesClient, ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRatesClient = exchangeRatesClient;
		this.exchangeRateRepository = exchangeRateRepository;
	}
	
	private void saveOrUpdateLatestCurrencyData(List<ExchangeRatesResponse>  exchangeRatesResponses) {
		log.info("Starting saveOrUpdateLatestCurrencyData");
		List<ExchangeRate> newRates = new ArrayList<>();
		List<ExchangeRate> updatedRates = new ArrayList<>();
		exchangeRatesResponses.forEach( (exchangeRatesResponse)->{
			Currency baseCurrency = exchangeRatesResponse.getBase();
			exchangeRatesResponse.getRates().
				forEach((targetCurrency, newConversionRate) -> {
					Optional<ExchangeRate> oldExchangeRate = exchangeRateRepository.findByBaseCurrencyAndToCurrency(baseCurrency, targetCurrency);
					if(oldExchangeRate.isPresent()) {
						ExchangeRate er = oldExchangeRate.get();
						er.setRate(newConversionRate);
						er.setTimestamp(exchangeRatesResponse.getTimestamp());
						updatedRates.add(er);
					}
					else {
						ExchangeRate er = ExchangeRate.builder()
								.baseCurrency(baseCurrency)
								.toCurrency(targetCurrency)
								.rate(newConversionRate)
								.timestamp(exchangeRatesResponse.getTimestamp())
								.build();
						newRates.add(er);
					}
				} );
		});
		exchangeRateRepository.saveAll(newRates);
		log.info("Saved {} new exchange rates.",newRates.size());
		exchangeRateRepository.saveAll(updatedRates);
		log.info("Updated {} exchange rates.",updatedRates.size());
	}
	
	public void getLatestExchangeRates() {
		log.info("Fetching latest exchange rates!");
		List<ExchangeRatesResponse> newExchangeRates = new ArrayList<>();
		String targetCurrencies = Currency.stream()
				.map(Enum::toString)
				.collect(Collectors.joining(",")); //Currencies for which we should get conversion rates
		Currency.stream().
			forEach(baseCurrency ->{		
				log.info("Fetcing currency conversions for base {} to {}",baseCurrency,targetCurrencies);
				ExchangeRatesResponse  exchangeRatesResponse = exchangeRatesClient.get(exchangeRatesAPIKey, baseCurrency.toString(), targetCurrencies);
				log.debug("Exchange rates are {}",exchangeRatesResponse);
				if(exchangeRatesResponse.isSuccess())
					newExchangeRates.add(exchangeRatesResponse);
				else
					log.error("Fetching exchangerates from apilayer failed!");
			});
		saveOrUpdateLatestCurrencyData(newExchangeRates);
	}

}
