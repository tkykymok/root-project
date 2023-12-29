package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.ValueObject;

import java.io.Serializable;

public record OrderItemKey(
        OrderId orderId,
        SeqNo seqNo
) implements Serializable, ValueObject {
    public static OrderItemKey of(OrderId orderId, SeqNo seqNo) {
        return new OrderItemKey(orderId, seqNo);
    }
}
