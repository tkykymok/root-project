package com.example.mainservice.domain.event.order;

import com.example.mainservice.domain.model.order.Order;
import com.example.shared.domain.event.DomainEvent;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime createAt;

    public OrderEvent(Order order, ZonedDateTime createAt) {
        this.order = order;
        this.createAt = createAt;
    }
}
