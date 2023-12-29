package com.example.mainservice.application.usecase.order;

import java.math.BigDecimal;

public record OrderItemInput(
        Long orderItemId,
        Long productId,
        BigDecimal price,
        Integer quantity) {
}
