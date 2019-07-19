package com.saothienhat.cartservice;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@EnableEurekaClient
public class CartApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CartApplication.class, args);
		
		LOGGER.info("### SPRING VERSION: " + SpringVersion.getVersion());
        System.out.println("List of beans provided by Spring-Boot: ");

//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames); 
//        for (String beanName : beanNames) {
//            LOGGER.info("\tBEAN: " + beanName);
//        }
	}

}
