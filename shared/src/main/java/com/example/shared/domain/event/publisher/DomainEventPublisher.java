package com.example.shared.domain.event.publisher;


public interface DomainEventPublisher<T> {

    void publish(T event);
}
