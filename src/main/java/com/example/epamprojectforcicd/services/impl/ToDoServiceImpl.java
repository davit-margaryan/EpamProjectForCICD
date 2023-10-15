package com.example.epamprojectforcicd.services.impl;

import com.example.epamprojectforcicd.dto.ToDoRequestDto;
import com.example.epamprojectforcicd.model.Client;
import com.example.epamprojectforcicd.model.Todo;
import com.example.epamprojectforcicd.repository.ClientRepository;
import com.example.epamprojectforcicd.repository.ToDoRepository;
import com.example.epamprojectforcicd.services.ToDoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;

    private final ClientRepository clientRepository;


    @Override
    public Todo createTodoForClient(long clientId, ToDoRequestDto toDoRequestDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("client not found with ID: " + clientId));
        Todo todo = new Todo();
        todo.setTitle(toDoRequestDto.getTitle());
        todo.setClient(client);
        return toDoRepository.save(todo);
    }


    @Override
    public Optional<Todo> getTodo(long todoId) {
        return toDoRepository.findById(todoId);
    }

}
