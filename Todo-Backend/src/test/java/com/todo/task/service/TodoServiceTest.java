package com.todo.task.service;

import com.todo.task.exception.TodoNotFoundException;
import com.todo.task.model.Todo;
import com.todo.task.repository.TodoRepository;
import com.todo.task.response.CustomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTodo() {
        Todo inputTodo = new Todo();
        inputTodo.setName("Test Todo");

        Todo createdTodo = new Todo();
        createdTodo.setId(1L);
        createdTodo.setName("Test Todo");

        when(todoRepository.save(any(Todo.class))).thenReturn(createdTodo);

        ResponseEntity<CustomResponse<Todo>> response = todoService.createTodo(inputTodo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdTodo, response.getBody().getData());
    }

    @Test
    public void testCreateTodoWithInvalidInput() {
        Todo inputTodo = new Todo();

        ResponseEntity<CustomResponse<Todo>> response = todoService.createTodo(inputTodo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData());
    }

    @Test
    public void testGetTodo() {
        long todoId = 1L;

        Todo todo = new Todo();
        todo.setId(todoId);
        todo.setName("Test Todo");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        ResponseEntity<CustomResponse<Todo>> response = todoService.getTodo(todoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todo, response.getBody().getData());
    }

    @Test
    public void testGetTodoNotFound() {
        long todoId = 1L;

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        ResponseEntity<CustomResponse<Todo>> response = todoService.getTodo(todoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getData());
    }

    @Test
    public void testGetAllTodos() {
        List<Todo> todos = new ArrayList<>();
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("Todo 1");
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("Todo 2");
        todos.add(todo1);
        todos.add(todo2);

        when(todoRepository.findAll()).thenReturn(todos);

        ResponseEntity<CustomResponse<List<Todo>>> response = todoService.findAllTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todos, response.getBody().getData());
    }

    @Test
    public void testDeleteTodo() {
        long todoId = 1L;

        ResponseEntity<CustomResponse<Todo>> response = todoService.deleteTodo(todoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().getData());

        verify(todoRepository, times(1)).deleteById(todoId);
    }

    @Test
    public void testDeleteTodoNotFound() {
        long todoId = 1L;

        doThrow(new TodoNotFoundException("Todo not found")).when(todoRepository).deleteById(todoId);

        ResponseEntity<CustomResponse<Todo>> response = todoService.deleteTodo(todoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getData());
    }

    @Test
    public void testUpdateTodo() {
        long todoId = 1L;

        Todo inputTodo = new Todo();
        inputTodo.setName("Updated Todo");

        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setName("Existing Todo");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        ResponseEntity<CustomResponse<Todo>> response = todoService.updateTodo(todoId, inputTodo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingTodo, response.getBody().getData());
    }

    @Test
    public void testUpdateTodoNotFound() {
        long todoId = 1L;

        Todo inputTodo = new Todo();
        inputTodo.setName("Updated Todo");

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        ResponseEntity<CustomResponse<Todo>> response = todoService.updateTodo(todoId, inputTodo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getData());
    }
}
