package com.example.shared.infrastructure.messaging;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsPublisherService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.sqs.queue.url}")
    private String sqsEndPoint;

    public void sendMessage(String message) {
        queueMessagingTemplate.convertAndSend(sqsEndPoint, message);
    }
}
