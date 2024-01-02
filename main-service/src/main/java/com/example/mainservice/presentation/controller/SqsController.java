package com.example.mainservice.presentation.controller;

import com.example.shared.infrastructure.messaging.SQSEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SQSEventPublisher sqsEventPublisher;

    @Value("${cloud.aws.sqs.url}")
    private String queueUrl;

    @PostMapping("/send")
    public String sendMessageToSqs(@RequestBody JsonNode message) {
        sqsEventPublisher.publishEvent(queueUrl, message);
        return "Message sent to SQS: " + message;
    }
}
