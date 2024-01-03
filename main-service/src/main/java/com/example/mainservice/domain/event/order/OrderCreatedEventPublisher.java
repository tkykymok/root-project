package com.example.mainservice.domain.event.order;

import com.example.shared.domain.event.publisher.DomainEventPublisher;


public interface OrderCreatedEventPublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
