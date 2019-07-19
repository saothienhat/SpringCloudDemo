package com.saothienhat.exportdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExportdataserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExportdataserviceApplication.class, args);
	}

}
