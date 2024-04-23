package com.example.mainservice.application.usecase.todo;

import com.example.mainservice.domain.model.task.Task;
import com.example.mainservice.domain.model.valueobject.TaskId;
import com.example.mainservice.domain.repository.task.TaskRepository;
import com.example.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchTodoUsecase extends Usecase<Void, Void> {


    @Override
    public Void execute(Void input) {
        return null;
    }
}
