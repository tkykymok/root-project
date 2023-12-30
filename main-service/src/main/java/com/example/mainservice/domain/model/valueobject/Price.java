package com.example.mainservice.domain.model.valueobject;

import java.math.BigDecimal;


public record Price(BigDecimal value) {
    public static Price of(BigDecimal value) {
        return new Price(value);
    }
}