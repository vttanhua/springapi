package com.example.sa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.sa.client.ExchangeRatesClient;
import com.example.sa.entity.SchedulerJobInfo;
import com.example.sa.service.SchedulerJobService;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringapiApplication  implements CommandLineRunner{


	@Value("${springapi.exchangeRatesCronExpression}")
	private String exchangeRatesCronExpression;
	
	@Value("${springapi.exchangeRatesJobClass}")
	private String exchangeRatesJobClass;
	
	@Value("${springapi.exchangeRatesBaseURL}")
	private String exchangeRatesBaseURL;
	
    @Autowired
    private SchedulerJobService schedulerJobService;
	
	@Bean
	public ExchangeRatesClient buildExchangeRatesClient() {
		return Feign.builder()
			.decoder(new JacksonDecoder())
			.encoder(new JacksonEncoder())
			.target(ExchangeRatesClient.class, this.exchangeRatesBaseURL);
	}
    
	public static void main(String[] args) {
		SpringApplication.run(SpringapiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Running command line runner ***************************");
		SchedulerJobInfo schedulerJob = new SchedulerJobInfo();
		schedulerJob.setJobName("Get exchange rates");
		schedulerJob.setJobGroup("batch");
		schedulerJob.setJobStatus("NORMAL");
		schedulerJob.setCronExpression(this.exchangeRatesCronExpression);
		schedulerJob.setJobClass(this.exchangeRatesJobClass);
		schedulerJob.setDesc("get exchange rates on every hour.");  
		schedulerJobService.saveOrupdate(schedulerJob);
		schedulerJobService.startJobNow(schedulerJob);
	}

}
