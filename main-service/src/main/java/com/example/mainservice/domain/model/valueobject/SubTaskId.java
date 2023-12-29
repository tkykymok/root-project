package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.BaseId;
import com.example.mainservice.domain.model.ValueObject;

public record SubTaskId(Long value) implements BaseId<Long>, ValueObject {
    public static SubTaskId of(Long value) {
        return new SubTaskId(value);
    }
}
