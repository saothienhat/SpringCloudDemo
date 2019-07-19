package com.saothienhat.bankservice.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Binh Trinh
 * This contains all of Project configurations
 */
@Component
@ConfigurationProperties(prefix="bank-service")
public class AppConfiguration {	
	@Value("${project.name: Default Project name}")
	private String projectName;
	@Setter
	@Getter
	private Double minBalance;
	@Setter
	@Getter
	private Double maxBalance;
	@Getter
	@Setter
	private String serviceName;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String functionName;
	
	
	@Override
	public String toString() {
		String msg = "serviceName: " + this.serviceName + " - description: " + this.description + " - functionName: " + this.functionName
					+ " - minBalance: " + this.minBalance + " - maxBalance: " + this.maxBalance
					+ " - Project name: " + this.projectName;					 
		return msg;
	}
}
