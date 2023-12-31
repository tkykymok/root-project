package com.example.mainservice.presentation.controller;

import com.example.shared.infrastructure.messaging.SqsPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsPublisherService sqsPublisherService;

    @PostMapping("/send")
    public String sendMessageToSqs(@RequestBody String message) {
        sqsPublisherService.sendMessage(message);
        return "Message sent to SQS: " + message;
    }
}
