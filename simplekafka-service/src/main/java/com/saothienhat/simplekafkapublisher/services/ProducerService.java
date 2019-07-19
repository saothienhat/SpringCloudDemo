package com.saothienhat.simplekafkapublisher.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.saothienhat.simplekafkapublisher.model.SimpleKafkaConst;

@Service
public class ProducerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * @param message
     */
    public void sendMessage(String message) {
    	LOGGER.info(String.format("#### -> ProducerService :: Producing message -> %s", message));
        this.kafkaTemplate.send(SimpleKafkaConst.TOPICS.BINH_ASSIGNMENT_TOPIC_01, message);
    }

}
