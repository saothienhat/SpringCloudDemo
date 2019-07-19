package com.saothienhat.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApigatewayApplication.class);

	public static void main(String[] args) {
		LOGGER.info("API Gateway running...");
		SpringApplication.run(ApigatewayApplication.class, args);
	}

}
