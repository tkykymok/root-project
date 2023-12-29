package com.example.mainservice.domain.queryservice.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record OrderDetailsResult(
        Long id,
        LocalDate orderDate,
        BigDecimal totalAmount,
        String productName,
        BigDecimal productPrice,
        Integer quantity,
        BigDecimal subTotalAmount) {
}