package com.example.mainservice.infrastructure.messaging.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSTestEventPublisher {

    private final SqsAsyncClient sqsAsyncClient;

    public void publish(String message) {
        log.info("Generating event : {}", message);
        SendMessageRequest sendMessageRequest;
        sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("http://localstack:4566/000000000000/test-queue")
                .messageBody(message)
                .build();
        sqsAsyncClient.sendMessage(sendMessageRequest);
        log.info("Event has been published in SQS.");
    }

}
