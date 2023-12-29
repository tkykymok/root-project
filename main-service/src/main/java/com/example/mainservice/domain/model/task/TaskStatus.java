package com.example.mainservice.domain.model.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    UNCOMPLETED(0, "未完了"),
    IN_PROGRESS(1, "進行中"),
    COMPLETED(2, "完了");

    private final Integer code;
    private final String description;

    TaskStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TaskStatus valueOf(Integer code) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("TaskStatus is not found. code: " + code);
    }
}
