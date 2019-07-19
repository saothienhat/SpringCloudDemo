package com.saothienhat.simplekafkapublisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.simplekafkapublisher.services.ProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);
	private final ProducerService producerService;

    @Autowired
    KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
    	LOGGER.info(String.format("#### -> KafkaController :: sending message -> %s", message));
        this.producerService.sendMessage(message);
    }

}
