package com.example.mainservice.application.usecase.order;

import java.util.List;

public record UpdateOrderInput(
        Long userId,
        Long orderId,
        Long version,
        List<OrderItemInput> orderItems) {
}
