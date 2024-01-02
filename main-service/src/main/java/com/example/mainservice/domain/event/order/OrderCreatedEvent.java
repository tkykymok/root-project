package com.example.mainservice.domain.event.order;

import com.example.mainservice.domain.model.order.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {
    public OrderCreatedEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }
}
