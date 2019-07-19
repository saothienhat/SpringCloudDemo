package com.saothienhat.eurekaserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(EurekaserverApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Eureka Server running...");
		SpringApplication.run(EurekaserverApplication.class, args);
	}

}
