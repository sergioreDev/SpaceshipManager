package com.sergio.spaceShipManager.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = {"spaceshipTopic", "showTopic", "movieTopic"}, groupId="myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
