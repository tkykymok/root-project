package com.example.mainservice.application.usecase.task;


public record UpdateTaskInput(
        Long taskId,
        String subTaskTitle,
        String subTaskContent
) {
}
