package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.BaseId;
import com.example.mainservice.domain.model.ValueObject;

public record OrderId(Long value) implements BaseId<Long>, ValueObject {
    public static OrderId of(Long value) {
        return new OrderId(value);
    }
}
