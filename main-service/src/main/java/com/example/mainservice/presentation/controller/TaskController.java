package com.example.mainservice.presentation.controller;

import com.example.mainservice.application.usecase.task.UpdateTaskInput;
import com.example.mainservice.application.usecase.task.FetchTaskUsecase;
import com.example.mainservice.application.usecase.task.UpdateTaskUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final FetchTaskUsecase fetchTaskUsecase;
    private final UpdateTaskUsecase updateTaskUsecase;

    public TaskController(FetchTaskUsecase fetchTaskUsecase, UpdateTaskUsecase updateTaskUsecase) {
        this.fetchTaskUsecase = fetchTaskUsecase;
        this.updateTaskUsecase = updateTaskUsecase;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {

        return ResponseEntity.ok(fetchTaskUsecase.execute(taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId) {

        UpdateTaskInput input = new UpdateTaskInput(taskId, "サブタスクのタイトル", "サブタスクの内容");
        updateTaskUsecase.execute(input);

        return ResponseEntity.ok(null);
    }

}
