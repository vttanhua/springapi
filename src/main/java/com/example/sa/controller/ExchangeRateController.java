package com.example.sa.controller;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.service.ExchangeRatesFetchService;
import com.example.sa.service.ExchangeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/exchange")
public class ExchangeRateController {

	ExchangeService exchangeService;
	
	@Autowired
	public ExchangeRateController(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}
	
	@GetMapping(value = "/{fromCurrency}/{toCurrency}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeResponse getExchangeData( @PathVariable("fromCurrency") Currency fromCurrency
    								, @PathVariable("toCurrency") Currency toCurrency
    								, @PathVariable("amount") Double amount) {
		
		return exchangeService.getExchange(fromCurrency, toCurrency, amount);
	}
	
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> return404(NoSuchElementException ex) {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", LocalDateTime.now());
    	body.put("status", HttpStatus.NOT_FOUND);
    	body.put("error", ex.getMessage());
        log.error("Unable to complete transaction", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON.toString());
        return new ResponseEntity<>(body,headers,HttpStatus.NOT_FOUND);

    }	
	
}
