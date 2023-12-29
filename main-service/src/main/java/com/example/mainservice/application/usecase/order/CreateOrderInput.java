package com.example.mainservice.application.usecase.order;

import java.util.List;

public record CreateOrderInput(
        Long userId,
        List<OrderItemInput> orderItems
) {
}
