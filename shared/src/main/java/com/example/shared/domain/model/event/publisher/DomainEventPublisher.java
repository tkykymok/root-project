package com.example.shared.domain.model.event.publisher;


import com.example.shared.domain.model.event.DomainEvent;

public interface DomainEventPublisher<T> {

    void publish(T event);
}
