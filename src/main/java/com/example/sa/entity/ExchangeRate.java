package com.example.sa.entity;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.sa.enums.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class ExchangeRate extends BaseEntity{
	
	public ExchangeRate() {}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Builder.Default
	private Long id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Currency baseCurrency;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Currency toCurrency;
	
	@Column
	private Double rate;
	
	@Column
	private Long timestamp;
	
}

