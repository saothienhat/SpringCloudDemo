package com.saothienhat.configserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Config Server running...");
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
