package com.example.sa.controller;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.example.sa.dto.ExchangeResponse;
import com.example.sa.enums.Currency;
import com.example.sa.service.ExchangeService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {
	
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
    
    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();
    
    
    @BeforeEach
	void setUpBeforeClass() throws Exception {

	}
    
    
    @Test
    public void getExchangeOKCase() {
    	log.info("Starting getExchangeOKCase()*****");
    	when(exchangeServiceMock.getExchange(FROM_CURRENCY, TO_CURRENCY, FROM_AMOUNT)).thenReturn(exchangeResponseMock);

    	when(exchangeResponseMock.getToAmount()).thenReturn(TO_AMOUNT);
    	ExchangeResponse response = exchangeRateController.getExchangeData(FROM_CURRENCY, TO_CURRENCY, FROM_AMOUNT);

		assertThat(response.getToAmount(), is(TO_AMOUNT));
    }
    
    @Test
    public void getExchangeNotFoundCase() {
    	log.info("Starting getExchangeNotFoundCase()*****");
    	when(exchangeServiceMock.getExchange(FROM_CURRENCY, TO_CURRENCY, FROM_AMOUNT))
    		.thenThrow(new NoSuchElementException(String.format("No conversion from %s to %s exists!",FROM_CURRENCY,  TO_CURRENCY)));

    	NoSuchElementException exception = Assertions.assertThrowsExactly(NoSuchElementException.class, () -> {
        	ExchangeResponse response = exchangeRateController.getExchangeData(FROM_CURRENCY, TO_CURRENCY, FROM_AMOUNT);

    	});

    	Assertions.assertEquals("No conversion from EUR to USD exists!", exception.getMessage());
    }

}
