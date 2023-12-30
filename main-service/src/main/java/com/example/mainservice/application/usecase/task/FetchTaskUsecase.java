package com.example.mainservice.application.usecase.task;

import com.example.mainservice.domain.model.task.Task;
import com.example.mainservice.domain.model.valueobject.TaskId;
import com.example.mainservice.domain.repository.task.TaskRepository;
import com.example.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchTaskUsecase extends Usecase<Long, Task> {

    private final TaskRepository taskRepository;

    @Override
    public Task execute(Long taskId) {
        // タスクの取得
        Task task = taskRepository.findById(TaskId.of(taskId));

        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        // タスクが存在しない場合はnullを返す
        return task;
    }
}
