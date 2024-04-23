package com.example.mainservice.presentation.controller;

import com.example.mainservice.application.usecase.task.UpdateTaskInput;
import com.example.mainservice.application.usecase.task.FetchTaskUsecase;
import com.example.mainservice.application.usecase.task.UpdateTaskUsecase;
import com.example.mainservice.application.usecase.todo.FetchTodoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final FetchTaskUsecase fetchTaskUsecase;
    private final UpdateTaskUsecase updateTaskUsecase;
    private final FetchTodoUsecase fetchTodoUsecase;

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {

        return ResponseEntity.ok(fetchTaskUsecase.execute(taskId));
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTodo() {

        return ResponseEntity.ok(fetchTodoUsecase.execute(null));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId) {

        UpdateTaskInput input = new UpdateTaskInput(taskId, "サブタスクのタイトル", "サブタスクの内容");
        updateTaskUsecase.execute(input);

        return ResponseEntity.ok(null);
    }

}
