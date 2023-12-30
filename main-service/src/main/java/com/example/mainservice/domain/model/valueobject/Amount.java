package com.example.mainservice.domain.model.valueobject;

import java.math.BigDecimal;


public record Amount(BigDecimal value) {
    public static Amount of(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("value is null");
        if (value.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("value is negative");

        return new Amount(value);
    }

    public Amount add(Amount other) {
        return new Amount(this.value.add(other.value()));
    }

    public static Amount calculateTotalFromPriceAndQuantity(Price price, Quantity quantity) {
        if (price == null || quantity == null) throw new IllegalArgumentException("price or quantity is null");
        return new Amount(price.value().multiply(BigDecimal.valueOf(quantity.value())));
    }

}