package com.example.sa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sa.entity.ExchangeRate;
import com.example.sa.enums.Currency;

@Repository
public interface ExchangeRateRepository  extends JpaRepository<ExchangeRate, Long> {
	Optional<ExchangeRate> findByBaseCurrencyAndToCurrency(Currency base, Currency to);
}
