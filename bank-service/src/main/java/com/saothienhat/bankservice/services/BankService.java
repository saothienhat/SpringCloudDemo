package com.saothienhat.bankservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saothienhat.bankservice.model.AppConfiguration;

@Service
public class BankService {
	
	@Autowired
	private AppConfiguration appConfig;
	
	
	public AppConfiguration getConfigFromConfigServer() {
		return this.appConfig;
	}

}
