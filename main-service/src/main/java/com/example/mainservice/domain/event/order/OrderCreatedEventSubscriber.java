package com.example.mainservice.domain.event.order;

import com.example.shared.domain.event.subscriber.DomainEventSubscriber;

public interface OrderCreatedEventSubscriber extends DomainEventSubscriber<OrderCreatedEvent> {
}