package com.example.sa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

import com.example.sa.client.ExchangeRatesClient;
import com.example.sa.dto.ExchangeRatesResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ExchangeRatesFetchServiceTest {

    @Mock
    private ExchangeRatesClient exchangeRatesClient;
    
    private String exchangeDataResponse = "{\r\n"
    		+ "    \"success\": true,\r\n"
    		+ "    \"timestamp\": 1653646337,\r\n"
    		+ "    \"base\": \"EUR\",\r\n"
    		+ "    \"date\": \"2022-05-27\",\r\n"
    		+ "    \"rates\": {\r\n"
    		+ "        \"EUR\": 1,\r\n"
    		+ "        \"USD\": 1.070997,\r\n"
    		+ "        \"SEK\": 10.557709\r\n"
    		+ "    }\r\n"
    		+ "}";
    
    private Double EUR_TO_SEK_RATE = 10.557709;
    private Double EUR_TO_USD_RATE = 1.070997;
    
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    
    @Mock
    private ExchangeRate exchangeRateOriginalMock;
    
    @Mock
    private ExchangeRatesResponse exchangeRatesResponseMock;
    

    @InjectMocks
    private ExchangeRatesFetchService service;
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ExchangeRate exchangeRateOriginal = ExchangeRate.builder().baseCurrency(Currency.EUR).toCurrency(Currency.SEK).rate(2.5).build();
		String targetCurrencies = Currency.stream()
				.map(Enum::toString)
				.collect(Collectors.joining(","));
		ObjectMapper mapper = new ObjectMapper();
		ExchangeRatesResponse exchangeRatesResponse = mapper.readValue(exchangeDataResponse, ExchangeRatesResponse.class);
		log.info("Test rates are {}",exchangeRatesResponse);
		
		when(exchangeRatesClient.get(isNull(),eq(Currency.EUR.toString()),any(String.class))).thenReturn(exchangeRatesResponse);
		when(exchangeRatesClient.get(isNull(),eq(Currency.SEK.toString()),any(String.class))).thenReturn(exchangeRatesResponseMock);
		when(exchangeRatesClient.get(isNull(),eq(Currency.USD.toString()),any(String.class))).thenReturn(exchangeRatesResponseMock);
		
		when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(Currency.EUR, Currency.SEK)).thenReturn(Optional.of(exchangeRateOriginal));
		when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(Currency.EUR, Currency.EUR)).thenReturn(Optional.of(exchangeRateOriginalMock));
		when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(Currency.EUR, Currency.USD)).thenReturn(Optional.ofNullable(null));
		
		//not ever needed because of mocking the api call!
		//when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(eq(Currency.SEK),any(Currency.class))).thenReturn(Optional.of(exchangeRateOriginalMock));
		//when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(eq(Currency.USD),any(Currency.class))).thenReturn(Optional.of(exchangeRateOriginalMock));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIsExchangeRateUpdatedWhenAlreadyExists() {
		log.info("Starting testIsExchangeRateUpdatedWhenAlreadyExists*****");
		ArgumentCaptor<List<ExchangeRate>> a= ArgumentCaptor.forClass(List.class);
		service.getLatestExchangeRates();
		verify(exchangeRateRepository,times(2)).saveAll(a.capture());//Save and update separated
		List<List<ExchangeRate>> allRates = a.getAllValues();
		log.info("data is {}",allRates);
		assertEquals(EUR_TO_SEK_RATE,allRates.get(1).get(1).getRate());
		assertEquals(Currency.EUR,allRates.get(1).get(1).getBaseCurrency());
		assertEquals(Currency.SEK,allRates.get(1).get(1).getToCurrency());
	}
	
	@Test
	void testIsExchangeRateCreatedWhenNotExists() {
		log.info("Starting testIsExchangeRateCreatedWhenNotExists*****");
		ArgumentCaptor<List<ExchangeRate>> a= ArgumentCaptor.forClass(List.class);
		service.getLatestExchangeRates();
		verify(exchangeRateRepository,times(2)).saveAll(a.capture());//Save and update separated
		List<List<ExchangeRate>> allRates = a.getAllValues();
		log.info("data is {}",allRates);
		assertEquals(EUR_TO_USD_RATE,allRates.get(0).get(0).getRate());
		assertEquals(Currency.EUR,allRates.get(0).get(0).getBaseCurrency());
		assertEquals(Currency.USD,allRates.get(0).get(0).getToCurrency());
	}

}
