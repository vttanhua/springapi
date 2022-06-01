package com.example.sa.client;

import com.example.sa.dto.ExchangeRatesResponse;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ExchangeRatesClient {

	@RequestLine("GET exchangerates_data/latest?symbols={target}&base={base}")
	@Headers({"apiKey: {apiKey}"})
	public ExchangeRatesResponse get(@Param("apiKey") String apiKey, @Param("base") String base, @Param("target") String target);

}
