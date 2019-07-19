package com.saothienhat.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserserviceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserserviceApplication.class);

	public static void main(String[] args) {
		LOGGER.info("User Service running...");
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
