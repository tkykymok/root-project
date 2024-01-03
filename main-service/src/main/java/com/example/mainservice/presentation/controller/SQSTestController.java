package com.example.mainservice.presentation.controller;

import com.example.mainservice.infrastructure.messaging.publisher.SQSTestEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SQSTestController {

    private final SQSTestEventPublisher sqsTestEventPublisher;

    @PostMapping("/send")
    public ResponseEntity<?> createOrder(@RequestBody String message) {
        sqsTestEventPublisher.publish(message);
        return ResponseEntity.ok().build();
    }
}
