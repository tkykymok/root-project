package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.BaseId;
import com.example.mainservice.domain.model.ValueObject;

public record ProductId(Long value) implements BaseId<Long>, ValueObject {
    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}
