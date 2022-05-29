package com.example.sa.dto;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExchangeResponse {
	private Currency from;
	private Currency to;
	private Double toAmount;
	private Double exchangeRate;
	@Schema(description = "Epoch in seconds GTM")
	private Long timestamp;
	
	public ExchangeResponse() {}
}
