package com.example.sa.dto;

import com.example.sa.enums.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SSNValidatorResponse {

	private Boolean valid;
	private String errorMessage;
	
	public SSNValidatorResponse() {};
	
	public SSNValidatorResponse setData(Boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
		return this;
	}
}
