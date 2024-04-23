package com.example.mainservice.domain.model.valueobject;

public record TodoId(Integer value) implements ValueObject<Integer> {
    public static TodoId of(Integer value) {
        return new TodoId(value);
    }
}
