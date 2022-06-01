package com.example.sa.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sa.dto.SSNInput;
import com.example.sa.dto.SSNValidatorResponse;
import com.example.sa.enums.Country;
import com.example.sa.service.SSNService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/ssn")
public class SSNController {
	
	private SSNService ssnService;
	
	public SSNController(SSNService ssnService) {
		this.ssnService = ssnService;
	}
	
	
	@PostMapping(value = "/{country}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public SSNValidatorResponse validate(@PathVariable("country") Country country, @RequestBody SSNInput ssn) {
		
		return ssnService.validate(country, ssn.getSsn());
	}

}
