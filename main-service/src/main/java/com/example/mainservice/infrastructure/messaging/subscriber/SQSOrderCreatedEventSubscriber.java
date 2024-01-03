package com.example.mainservice.infrastructure.messaging.subscriber;

import com.example.mainservice.domain.event.order.OrderCreatedEvent;
import com.example.mainservice.domain.event.order.OrderCreatedEventSubscriber;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SQSOrderCreatedEventSubscriber implements OrderCreatedEventSubscriber {

    @SqsListener(
            value = "${spring.cloud.aws.sqs.queue-name}",
            pollTimeoutSeconds = "20",
            maxConcurrentMessages = "5",
            maxMessagesPerPoll = "5",
            messageVisibilitySeconds = "30"
    )
    @Override
    public void subscribe(OrderCreatedEvent event) {
        System.out.println("Event received from SQS : " + event);
        log.info("Event received from SQS : {}", event);
    }
}


