package com.example.sa.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.enums.Currency;
import com.example.sa.service.ExchangeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class Controller2 {

	@LocalServerPort
    private int port;
	
	private String BASE_URL;
	private static Currency FROM_CURRENCY = Currency.EUR;
	private static Currency TO_CURRENCY = Currency.USD;
	private static Double 	TO_AMOUNT = 100.0;
	private static Double EXCHANGE_RATE = 2.0;
	private static Long TIMESTAMP = 1653814335L;
    private static Double FROM_AMOUNT = 50.0;
	
	@Mock
    private ExchangeService exchangeServiceMock;
	
    @Mock
    private ExchangeResponse  exchangeResponseMock; 
	
	@InjectMocks
    private ExchangeRateController exchangeRateController;
    
    

   RestTemplate restTemplate = new RestTemplate();
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		BASE_URL = "http://localhost:"+port+"/api/v1/exchange/";
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		/*
		when(exchangeServiceMock.getExchange(FROM_CURRENCY, TO_CURRENCY, FROM_AMOUNT)).thenReturn(exchangeResponseMock);
		ExchangeResponse ex = exchangeRateController.getExchangeData(Currency.EUR, Currency.USD, FROM_AMOUNT);
		log.info("first time {}", ex);
		String exchangeURL = BASE_URL+FROM_CURRENCY.toString()+"/"+TO_CURRENCY.toString()+"/"+FROM_AMOUNT;
    	log.info("Called url is {} ",exchangeURL);
    	ResponseEntity<ExchangeResponse> response = restTemplate.exchange(exchangeURL, HttpMethod.GET,null,
                new ParameterizedTypeReference<ExchangeResponse>() {});
    	log.info("{}", ex);
    	*/
	}

}
