package com.saothienhat.simplekafkapublisher.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;;

@Service
public class ConsumerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

	@KafkaListener(topics = "binh-assignment-topic-01", groupId = "group_id")
    public void consume(String message) throws IOException {
		LOGGER.info(String.format("#### -> ConsumerService :: Consumed message -> %s", message));
    }
}
