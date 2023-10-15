package com.example.epamprojectforcicd;

import com.example.epamprojectforcicd.dto.ToDoRequestDto;
import com.example.epamprojectforcicd.model.Client;
import com.example.epamprojectforcicd.model.Todo;
import com.example.epamprojectforcicd.repository.ClientRepository;
import com.example.epamprojectforcicd.repository.ToDoRepository;
import com.example.epamprojectforcicd.services.impl.ToDoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceImplTest {

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTodoForClient_ValidRequest() {
        long clientId = 1L;
        String title = "Do something";
        ToDoRequestDto toDoRequestDto = new ToDoRequestDto();

        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Todo expectedTodo = new Todo();
        expectedTodo.setTitle(title);
        expectedTodo.setClient(client);

        when(toDoRepository.save(any(Todo.class))).thenReturn(expectedTodo);

        Todo createdTodo = toDoService.createTodoForClient(clientId, toDoRequestDto);

        verify(clientRepository, times(1)).findById(clientId);
        verify(toDoRepository, times(1)).save(any(Todo.class));
        assertEquals(clientId, createdTodo.getClient().getId());
        assertEquals(title, createdTodo.getTitle());
    }

    @Test
    void testCreateTodoForClient_InvalidClient() {
        long clientId = 2L;
        ToDoRequestDto toDoRequestDto = new ToDoRequestDto();

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> toDoService.createTodoForClient(clientId, toDoRequestDto));
        verify(toDoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testGetTodo_ExistingTodo() {
        long todoId = 1L;
        Todo expectedTodo = new Todo();
        when(toDoRepository.findById(todoId)).thenReturn(Optional.of(expectedTodo));

        Optional<Todo> retrievedTodo = toDoService.getTodo(todoId);

        verify(toDoRepository, times(1)).findById(todoId);
        assertTrue(retrievedTodo.isPresent());
        assertEquals(expectedTodo, retrievedTodo.get());
    }

    @Test
    void testGetTodo_NonExistentTodo() {
        long todoId = 2L;
        when(toDoRepository.findById(todoId)).thenReturn(Optional.empty());

        Optional<Todo> retrievedTodo = toDoService.getTodo(todoId);

        verify(toDoRepository, times(1)).findById(todoId);
        assertTrue(retrievedTodo.isEmpty());
    }
}

