package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.BaseId;
import com.example.mainservice.domain.model.ValueObject;

public record UserId(Long value) implements BaseId<Long>, ValueObject {
    public static UserId of(Long value) {
        return new UserId(value);
    }
}
