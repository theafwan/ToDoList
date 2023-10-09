package com.todo.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.task.model.Todo;
import com.todo.task.response.CustomResponse;
import com.todo.task.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
@Slf4j
public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateTodo() {
        Todo inputTodo = new Todo();
        inputTodo.setName("Test Todo");
        inputTodo.setDescription("Testing Todo");

        Todo createdTodo = new Todo();
        createdTodo.setId(1L);
        createdTodo.setName("Test Todo");
        createdTodo.setDescription("Testing Todo");

        when(todoService.createTodo(any(Todo.class))).thenReturn(new ResponseEntity<>(new CustomResponse<>(true, "Todo created", createdTodo), HttpStatus.CREATED));

        ResponseEntity<CustomResponse<Todo>> response = todoController.createTodo(inputTodo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTodo, response.getBody().getData());
    }

    @Test
    public void testGetTodo() {
        long todoId = 1L;

        Todo todo = new Todo();
        todo.setId(todoId);
        todo.setName("Test Todo");

        when(todoService.getTodo(todoId)).thenReturn(new ResponseEntity<>(new CustomResponse<>(true, "Todo found", todo), HttpStatus.OK));

        ResponseEntity<CustomResponse<Todo>> response = todoController.getTodo(todoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todo, response.getBody().getData());
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

        when(todoService.findAllTodos()).thenReturn(new ResponseEntity<>(new CustomResponse<>(true, "Todos found", todos), HttpStatus.OK));

        ResponseEntity<CustomResponse<List<Todo>>> response = todoController.getAllTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todos, response.getBody().getData());
    }

    @Test
    public void testRemoveTodo() {
        long todoId = 1L;

        ResponseEntity<CustomResponse<Todo>> response = todoController.removeTodo(todoId);
        assertNull(response);

        verify(todoService, times(1)).deleteTodo(todoId);
    }

    @Test
    public void testUpdateTodo() {
        long todoId = 1L;

        Todo inputTodo = new Todo();
        inputTodo.setName("Updated Todo");

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setName("Updated Todo");

        when(todoService.updateTodo(eq(todoId), any(Todo.class))).thenReturn(new ResponseEntity<>(new CustomResponse<>(true, "Todo updated", updatedTodo), HttpStatus.OK));

        ResponseEntity<CustomResponse<Todo>> response = todoController.updateTodo(todoId, inputTodo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTodo, response.getBody().getData());
    }

    @Test
    public void testGetTodo_NotFound() {
        long todoId = 999L; // Non-existent ID

        when(todoService.getTodo(todoId)).thenReturn(new ResponseEntity<>(new CustomResponse<>(false, "Todo not found", null), HttpStatus.NOT_FOUND));

        ResponseEntity<CustomResponse<Todo>> response = todoController.getTodo(todoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getData());
    }
    @Test
    public void testCreateTodo_BadRequest() {
        Todo inputTodo = new Todo(); // Missing required fields

        when(todoService.createTodo(any(Todo.class))).thenReturn(new ResponseEntity<>(new CustomResponse<>(false, "Invalid input", null), HttpStatus.BAD_REQUEST));

        ResponseEntity<CustomResponse<Todo>> response = todoController.createTodo(inputTodo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData());
    }

    @Test
    public void testCreateTodo_InternalServerError() {
        Todo inputTodo = new Todo();
        inputTodo.setName("Test Todo");
        inputTodo.setDescription("Testing Todo");

        when(todoService.createTodo(any(Todo.class))).thenReturn(new ResponseEntity<>(new CustomResponse<>(false, "Internal server error", null), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<CustomResponse<Todo>> response = todoController.createTodo(inputTodo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getData());
    }
}