package com.example.sa.enums;

import java.util.stream.Stream;

public enum Currency {
	 EUR, SEK, USD;
	 
	 public static Stream<Currency> stream() {
	     return Stream.of(Currency.values()); 
	 }
}
