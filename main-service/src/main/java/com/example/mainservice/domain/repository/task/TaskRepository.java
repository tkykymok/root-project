package com.example.mainservice.domain.repository.task;

import com.example.mainservice.domain.model.task.Task;
import com.example.mainservice.domain.model.valueobject.TaskId;


public interface TaskRepository {
    Task findById(TaskId id);

    void insert(Task task);

    void update(Task task);
}
