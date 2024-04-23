package com.example.mainservice.domain.model.valueobject;

public record Title(String value) implements ValueObject<String> {
    public static Title of(String value) {
        return new Title(value);
    }
}
