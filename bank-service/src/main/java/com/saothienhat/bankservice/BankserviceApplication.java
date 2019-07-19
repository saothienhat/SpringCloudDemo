package com.saothienhat.bankservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class BankserviceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankserviceApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Bank Service runnning...");
		SpringApplication.run(BankserviceApplication.class, args);
	}

}
