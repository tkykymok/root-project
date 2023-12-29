package com.example.mainservice.domain.model.valueobject;

import com.example.mainservice.domain.model.BaseId;
import com.example.mainservice.domain.model.ValueObject;

public record TaskId(Long value) implements BaseId<Long>, ValueObject {
    public static TaskId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("TaskIdは必須です");
        }
        return new TaskId(value);
    }
}
