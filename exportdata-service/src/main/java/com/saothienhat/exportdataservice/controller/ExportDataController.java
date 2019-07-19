package com.saothienhat.exportdataservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.exportdataservice.service.ExportDataService;

@RestController
@RequestMapping("/export")
public class ExportDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataController.class);

	@GetMapping("/excel")
	public void exportExcel() {
		LOGGER.info("Checking User Service status...");
//			return null;
	}
}
