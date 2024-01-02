package com.example.shared.infrastructure.messaging;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsSubscriberService {

    @SqsListener("test-queue.fifo")
    public void receiveMessageFromTestQueue(String message) {
        System.out.println("Message Received from test-queue: " + message);
    }

}