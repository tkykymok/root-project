package com.example.shared.domain.event.subscriber;


public interface DomainEventSubscriber<T> {

    void subscribe(T event);
}
