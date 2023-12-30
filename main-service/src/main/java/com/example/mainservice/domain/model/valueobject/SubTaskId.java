package com.example.mainservice.domain.model.valueobject;

import com.example.shared.domain.model.BaseId;

public record SubTaskId(Long value) implements BaseId<Long> {
    public static SubTaskId of(Long value) {
        return new SubTaskId(value);
    }
}
