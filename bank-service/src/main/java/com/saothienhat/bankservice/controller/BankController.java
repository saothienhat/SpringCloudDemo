package com.saothienhat.bankservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.saothienhat.bankservice.model.AppConfiguration;
import com.saothienhat.bankservice.model.BankConst;
import com.saothienhat.bankservice.services.BankService;

@RefreshScope
@RestController
@RequestMapping("/bank")
public class BankController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);
	
	@Autowired
	private BankService bankService;
	
	@Autowired
    private LoadBalancerClient loadBalancer;
	
	@GetMapping("/copyright") 
	public AppConfiguration getCopyrightFromConfigServer() {
		LOGGER.info("Bank Service getting copyright from Config Server...");
		return this.bankService.getConfigFromConfigServer();
	}

	/**
	 * Communicate with User Service using RestTempate to fetch User Service status
	 * @return userServiceStatus
	 */
	@GetMapping("/checkuserservice")
	public String checkUserServiceStatus() {
		String userServiceStatus = "Unknown";
		RestTemplate restTemplate = new RestTemplate();
		
		URI uri = loadBalancer.choose(BankConst.BANK_SVC_NAME).getUri();
		String url = uri.toString();
		LOGGER.info("Bank Service instance running at url: " + url);
		
//		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
//		LOGGER.info("Bank Service http-status: {}", result.getStatusCode());
//		LOGGER.info("Bank Service body: {}", result.getBody());

		URI userServiceUri = this.loadBalancer.choose(BankConst.USERS_SVC_NAME).getUri();
		String userSvcCheckStatusURL = userServiceUri.toString() + "/users/status/check";
		
		LOGGER.info("User Service instance running at url: " + userSvcCheckStatusURL);

		ResponseEntity<String> result = restTemplate.getForEntity(userSvcCheckStatusURL, String.class);
		userServiceStatus = (result != null) ? result.getBody() : "Unknown User Service status";
		LOGGER.info("User Service http-status: {}", result.getStatusCode());
		LOGGER.info("User Service body: {}", result.getBody());
		
		return userServiceStatus;
	}
	
}
