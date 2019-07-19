package com.saothienhat.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.userservice.model.UserServiceStatus;

@RestController
@RequestMapping("/users")
public class UsersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

	@GetMapping("/status/check")
	public UserServiceStatus getUserService() {
		LOGGER.info("Checking User Service status...");
		UserServiceStatus status = new UserServiceStatus("User Service", "Working");
		return status;
	}
}
