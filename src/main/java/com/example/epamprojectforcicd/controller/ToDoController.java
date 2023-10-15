package com.example.epamprojectforcicd.controller;

import com.example.epamprojectforcicd.dto.ToDoRequestDto;
import com.example.epamprojectforcicd.model.Todo;
import com.example.epamprojectforcicd.services.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todos")
@AllArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping
    public ResponseEntity<Todo> createToDoForClient(
            @RequestBody ToDoRequestDto toDoRequestDto,
            @RequestParam Long clientId) {
        Todo createdToDo = toDoService.createTodoForClient(clientId, toDoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
    }

    @GetMapping
    public ResponseEntity<?> getTodo(@RequestParam Long todoId) {
        Optional<Todo> toDo = toDoService.getTodo(todoId);
        if (toDo.isPresent()) {
            return ResponseEntity.ok(toDo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
