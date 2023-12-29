package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.ValueObject;


public record Quantity(Integer value) implements ValueObject {
    public static Quantity of(Integer i) {
        return new Quantity(i);
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value());
    }
}