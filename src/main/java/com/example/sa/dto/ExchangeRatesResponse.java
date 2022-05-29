package com.example.sa.dto;

import java.util.Date;
import java.util.Map;

import com.example.sa.enums.Currency;

import lombok.Data;

@Data
public class ExchangeRatesResponse {
	private boolean success;
	private long timestamp;
	private Currency base;
	private Date date;
	private Map<Currency,Double> rates;

}
