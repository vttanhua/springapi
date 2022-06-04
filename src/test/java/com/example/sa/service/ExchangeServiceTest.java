package com.example.sa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;
import com.example.sa.repository.ExchangeRateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {
	static ExchangeRate exchangeRateEURSEK;
	static ExchangeRate exchangeRateSEKEUR;
	
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    

    @InjectMocks
    private ExchangeService service;
    
    private Double exchangeAmount = 1000.0;


	
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		exchangeRateEURSEK = ExchangeRate.builder().baseCurrency(Currency.EUR).toCurrency(Currency.SEK).rate(10.0).build();
		exchangeRateSEKEUR = ExchangeRate.builder().baseCurrency(Currency.SEK).toCurrency(Currency.EUR).rate(0.1).build();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testThatConversionToAndFromSomeCurrencyReturnsOriginalAmount() {
		log.info("Starting testThatConversionToAndFromSomeCurrencyReturnsOriginalAmount*****");
		when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(Currency.EUR, Currency.SEK)).thenReturn(Optional.of(exchangeRateEURSEK));
		when(exchangeRateRepository.findByBaseCurrencyAndToCurrency(Currency.SEK, Currency.EUR)).thenReturn(Optional.of(exchangeRateSEKEUR));
		ExchangeResponse r1 = service.getExchange(Currency.EUR, Currency.SEK, this.exchangeAmount);
		ExchangeResponse r2 = service.getExchange(r1.getTo(), r1.getFrom(), r1.getToAmount());
		assertEquals(this.exchangeAmount,r2.getToAmount());
		assertNotEquals(r1.getToAmount(),r2.getToAmount());
		assertEquals(r1.getTo(),r2.getFrom());
		assertEquals(r1.getFrom(),r2.getTo());
		
	}

}
