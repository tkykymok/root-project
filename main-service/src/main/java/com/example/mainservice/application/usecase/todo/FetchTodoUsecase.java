package com.example.mainservice.application.usecase.todo;

import com.example.mainservice.domain.model.Todo;
import com.example.mainservice.infrastructure.repository.todo.TodoRepository;
import com.example.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchTodoUsecase extends Usecase<Void, List<Todo>> {

    private final TodoRepository todoRepository;

    @Override
    public List<Todo> execute(Void input) {

        var result = todoRepository.findAll();

        return result;
    }
}
