package com.saothienhat.userservice.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumerService.class);

	@KafkaListener(topics = "binh-assignment-topic-01", groupId = "group_id")
    public void consume(String message) throws IOException {
		LOGGER.info(String.format("#### user-service -> UserConsumerService [Kafka] :: Consumed message -> %s", message));
    }

}
