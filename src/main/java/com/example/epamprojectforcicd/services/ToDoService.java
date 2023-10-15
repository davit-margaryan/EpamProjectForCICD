package com.example.epamprojectforcicd.services;

import com.example.epamprojectforcicd.dto.ToDoRequestDto;
import com.example.epamprojectforcicd.model.Todo;

import java.util.Optional;

public interface ToDoService {

    Todo createTodoForClient(long clientId, ToDoRequestDto toDoRequestDto);

    Optional<Todo> getTodo(long todoId);

}
