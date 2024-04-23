package com.example.mainservice.infrastructure.repository.todo;

import com.example.mainservice.domain.model.Todo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodoRepository {

     List<Todo> findAll();
}
