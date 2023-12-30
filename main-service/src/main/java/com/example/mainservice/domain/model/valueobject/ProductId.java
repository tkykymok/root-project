package com.example.mainservice.domain.model.valueobject;

import com.example.shared.domain.model.BaseId;

public record ProductId(Long value) implements BaseId<Long> {
    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}
