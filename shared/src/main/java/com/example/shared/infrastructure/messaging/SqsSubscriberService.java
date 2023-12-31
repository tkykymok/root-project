package com.example.shared.infrastructure.messaging;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsSubscriberService {

    @SqsListener("test-queue")
    public void receiveMessage(String message) {
        System.out.println("Message Received: " + message);
    }
}