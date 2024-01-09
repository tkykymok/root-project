package com.example.mainservice.infrastructure.messaging.publisher;

import com.example.mainservice.domain.event.order.OrderCreatedEvent;
import com.example.mainservice.domain.event.order.OrderCreatedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSOrderCreatedEventPublisher implements OrderCreatedEventPublisher {

    @Value("${spring.cloud.aws.sqs.url}")
    private String queueUrl;

    private final SqsAsyncClient sqsAsyncClient;

    private final ObjectMapper objectMapper;

    @Override
    public void publish(OrderCreatedEvent event) {
        log.info("Generating event : {}", event);
        SendMessageRequest sendMessageRequest = null;
        try {
            sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(objectMapper.writeValueAsString(event))
                    // FIFO queueでのみ有効
//                    .messageGroupId(event.getClass().getSimpleName())
//                    .messageDeduplicationId(UUID.randomUUID().toString())
                    .build();
            sqsAsyncClient.sendMessage(sendMessageRequest);
            log.info("Event has been published in SQS.");
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }

}
