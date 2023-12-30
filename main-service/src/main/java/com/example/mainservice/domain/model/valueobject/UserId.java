package com.example.mainservice.domain.model.valueobject;

import com.example.shared.domain.model.BaseId;

public record UserId(Long value) implements BaseId<Long> {
    public static UserId of(Long value) {
        return new UserId(value);
    }
}
