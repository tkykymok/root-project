package com.example.mainservice.domain.model.valueobject;

import com.example.shared.domain.model.BaseId;

public record OrderId(Long value) implements BaseId<Long> {
    public static OrderId of(Long value) {
        return new OrderId(value);
    }
}
