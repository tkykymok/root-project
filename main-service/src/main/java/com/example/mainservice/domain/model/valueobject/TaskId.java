package com.example.mainservice.domain.model.valueobject;

import com.example.shared.domain.model.BaseId;

public record TaskId(Long value) implements BaseId<Long> {
    public static TaskId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("TaskIdは必須です");
        }
        return new TaskId(value);
    }
}
