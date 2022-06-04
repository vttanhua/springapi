package com.example.sa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sa.controller.ExchangeRateControllerTest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ExtendWith(MockitoExtension.class)
class SpringapiApplicationTests {


	@Test
	void contextLoads() {
		log.info("Starting contextLoads()*****");
	}

}
