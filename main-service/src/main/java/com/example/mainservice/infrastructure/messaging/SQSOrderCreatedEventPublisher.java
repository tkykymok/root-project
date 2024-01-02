package com.example.mainservice.infrastructure.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.mainservice.domain.event.order.OrderCreatedEvent;
import com.example.shared.domain.model.event.publisher.DomainEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSOrderCreatedEventPublisher implements DomainEventPublisher<OrderCreatedEvent> {

    @Value("${cloud.aws.sqs.url}")
    private String queueUrl;

    private final AmazonSQS amazonSQS;

    private final ObjectMapper objectMapper;

    @Override
    public void publish(OrderCreatedEvent event) {
        log.info("Generating event : {}", event);
        SendMessageRequest sendMessageRequest = null;
        try {
            sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(event))
                    // FIFO queueでのみ有効
                    .withMessageGroupId(event.getClass().getSimpleName())
                    .withMessageDeduplicationId(UUID.randomUUID().toString());
            amazonSQS.sendMessage(sendMessageRequest);
            log.info("Event has been published in SQS.");
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }

}
