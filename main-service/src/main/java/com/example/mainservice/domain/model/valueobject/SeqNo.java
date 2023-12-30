package com.example.mainservice.domain.model.valueobject;

public record SeqNo(Integer value) {
    public static SeqNo of(Integer value) {
        return new SeqNo(value);
    }
}
