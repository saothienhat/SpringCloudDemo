package com.saothienhat.apigatewayauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApigatewayauthApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApigatewayauthApplication.class);

	public static void main(String[] args) {
		LOGGER.info("API Gateway Auth running...");
		SpringApplication.run(ApigatewayauthApplication.class, args);
	}
	

}
