package com.example.mainservice.application.usecase.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FetchOrderOutput(Long id, LocalDate orderDate, BigDecimal totalAmount,
                               List<OrderItemOutput> orderItems) {

    public record OrderItemOutput(String productName, BigDecimal productPrice, Integer quantity, BigDecimal subTotalAmount) {
    }
}