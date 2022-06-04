package com.example.sa.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.dto.SSNInput;
import com.example.sa.dto.SSNValidatorResponse;
import com.example.sa.enums.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class SSNService {

	private static final String FIN_SSN_PATTERN = "^(\\d{6})([-+A])(\\d{3})(\\w){1}$";
	private static final String FIN_DATETIME_PATTERN = "ddMMyy";
	private static final int FIN_MODULO_BASE = 31;
	
	private static final Map<Integer,String> controlCharMap = Stream.of(new Object[][] { 
	     { 0, "0" }, { 1, "1" }, { 2, "2" },
	     { 3, "3" }, { 4, "4" }, { 5, "5" },
	     { 6, "6" }, { 7, "7" }, { 8, "8" },
	     { 9, "9" }, { 10, "A" }, { 11, "B" },
	     { 12, "C" }, { 13, "D" }, { 14, "E" },
	     { 15, "F" }, { 16, "H" }, { 17, "J" },
	     { 18, "K" }, { 19, "L" }, { 20, "M" },
	     { 21, "N" }, { 22, "P" }, { 23, "R" },
	     { 24, "S" }, { 25, "T" }, { 26, "U" },
	     { 27, "V" }, { 28, "W" }, { 29, "X" },
	     { 30, "Y" }
	 }).collect(Collectors.toMap(data ->  (Integer)data[0], data -> (String) data[1]));;
	
	/**
	 * Checks that given string is date using the given pattern
	 * @param datePart
	 * @param centurySeparator
	 * @param datePattern
	 * @return
	 */
	private SSNValidatorResponse validateBirthDatePart(String datePart, String centurySeparator, String datePattern) {
		log.info("Starting validateBirthDatePart");
		SSNValidatorResponse response = new SSNValidatorResponse(false, "Undefined internal error");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
   	 	try {
			LocalDate birthDate = LocalDate.parse(datePart, dateFormatter);
	   	 	log.info("parsed localdate is: {}", birthDate);
	   	 	response.setData(true, "");
   	 	}catch(DateTimeException dte) {
   	 		String errorMessage = String.format("Birthdate %s is not valid date.",datePart);
   	 		response.setData(false, errorMessage);
   	 		log.error(errorMessage);
   	 	}
		return response;
	}
	
	private SSNValidatorResponse validateControlCharacter(String datePart, String individualNumber, String checkChar) {
		log.info("Starting validateControlCharacter");
		SSNValidatorResponse response = new SSNValidatorResponse(false, String.format("Control character %s is not valid",checkChar));
		int sum = Integer.parseInt(datePart+individualNumber);
		int modulo = sum % FIN_MODULO_BASE;
		String expectedCheckChar = controlCharMap.get(modulo);
		if(checkChar.equals(expectedCheckChar))
			 return new SSNValidatorResponse(true, "");
		return response;
		
	}
	
	/**
	 * TODO/NOTE only checks that ssn date part is valid date not checking that it is valid for living person (not in future, not too old).
	 * TODO/NOTE Individual number effectively on range [002,899]
	 * @param country
	 * @param ssnInput
	 * @return
	 */
	public SSNValidatorResponse validate(Country country, String ssnInput) {
		log.info("Starting validate");
		SSNValidatorResponse response = new SSNValidatorResponse(false, "Undefined internal error");
		String ssn = ssnInput.toUpperCase();
		Pattern pattern = Pattern.compile(FIN_SSN_PATTERN, Pattern.CASE_INSENSITIVE);
		
		switch(country) {
			case FIN:
				Matcher matcher = pattern.matcher(ssn);
			    boolean matchFound = matcher.find();
			    if(matchFound && matcher.groupCount() == 4) {
			    	//groups are full ssn, birthdate, separator, checknumbers and control character
				    String fullSSN = matcher.group(0);
				    String birthDate = matcher.group(1); 
				    String separator = matcher.group(2);
				    String checkNumbers = matcher.group(3);
				    String controlCharacter = matcher.group(4);
			    	log.debug("part 0 {}",fullSSN);
				    log.debug("part 1 {}",birthDate);
				    log.debug("part 2 {}",separator);
				    log.debug("part 3 {}",checkNumbers);
				    log.debug("part 4 {}",controlCharacter);
			    	SSNValidatorResponse dateCheck = validateBirthDatePart(birthDate, separator, FIN_DATETIME_PATTERN);
			    	if(!dateCheck.getValid())
			    		return dateCheck;
			    	SSNValidatorResponse controlCheck = validateControlCharacter(birthDate, checkNumbers, controlCharacter);
			    	if(!controlCheck.getValid())
			    		return controlCheck;
			    }
			    else {
			    	return response.setData(false,String.format("SSN does not match with pattern %s.",FIN_SSN_PATTERN));
			    }
				return response.setData(true,"SSN is valid.");
			default:
				return response.setData(false,"Only FIN ssn is supported.");
		}
		
	}

}
