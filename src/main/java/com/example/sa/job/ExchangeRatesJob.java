package com.example.sa.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.example.sa.service.ExchangeRatesFetchService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@DisallowConcurrentExecution
public class ExchangeRatesJob extends QuartzJobBean {
	
	@Autowired
	ExchangeRatesFetchService exchangeRatesFetchService;
	
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
       log.info("ExchangeRatesJob Start................");
       exchangeRatesFetchService.getLatestExchangeRates();
       //log.info("SimpleJob End................");
       System.out.println("ExchangeRatesJob End................");
    }
}