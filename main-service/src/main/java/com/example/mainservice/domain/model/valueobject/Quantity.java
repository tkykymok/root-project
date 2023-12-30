package com.example.mainservice.domain.model.valueobject;


public record Quantity(Integer value) {
    public static Quantity of(Integer i) {
        return new Quantity(i);
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value());
    }
}