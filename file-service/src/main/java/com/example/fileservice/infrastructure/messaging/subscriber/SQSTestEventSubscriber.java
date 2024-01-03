package com.example.fileservice.infrastructure.messaging.subscriber;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SQSTestEventSubscriber {

    @SqsListener("test-queue")
    public void subscribe(String message) {
        log.info("Event received from SQS : {}", message);
    }

}


