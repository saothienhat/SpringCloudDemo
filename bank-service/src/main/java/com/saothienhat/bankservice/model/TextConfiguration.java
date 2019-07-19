package com.saothienhat.bankservice.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;

/**
 * @author Binh Trinh
 * This contains configurations related to text.*
 */
@Data
@Component
@ConfigurationProperties("text")
public class TextConfiguration {
	@Getter
	private String copyright;

	
}
