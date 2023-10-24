package com.todo.task.controller;

import com.todo.task.model.Todo;
import com.todo.task.response.CustomResponse;
import com.todo.task.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to handle HTTP requests related to Todos.
 */
@RestController
@RequestMapping("/api/v1/todos")
@CrossOrigin(origins = "*")
@Slf4j
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * Create a new Todo.
     *
     * @param todo The Todo object to be created, provided in the request body.
     * @return A ResponseEntity containing the created Todo and an appropriate HTTP status code.
     */
    @PostMapping
    public ResponseEntity<CustomResponse<Todo>> createTodo(@RequestBody Todo todo) {
        log.info("Creating a TODO :{}", todo);
        return todoService.createTodo(todo);
    }

    /**
     * Retrieve a Todo by its ID.
     *
     * @param id The ID of the Todo to retrieve, provided as a path variable.
     * @return A ResponseEntity containing the retrieved Todo and an appropriate HTTP status code.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Todo>> getTodo(@PathVariable long id) {
        log.info("Fetching a TODO with id :{}", id);
        return todoService.getTodo(id);
    }

    /**
     * Retrieve all Todos.
     *
     * @return A ResponseEntity containing a list of all Todos and an appropriate HTTP status code.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Todo>>> getAllTodos() {
        log.info("Fetching all the Todos");
        return todoService.findAllTodos();
    }

    /**
     * Delete a Todo by its ID.
     *
     * @param id The ID of the Todo to delete, provided as a path variable.
     * @return A ResponseEntity with no content and an appropriate HTTP status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Todo>> removeTodo(@PathVariable long id) {
        log.info("Deleting a TODO with id :{}", id);
        return todoService.deleteTodo(id);
    }

    /**
     * Update a Todo by its ID.
     *
     * @param id   The ID of the Todo to update, provided as a path variable.
     * @param todo The updated Todo object, provided in the request body.
     * @return A ResponseEntity containing the updated Todo and an appropriate HTTP status code.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Todo>> updateTodo(@PathVariable(value = "id") Long id,
                                                           @RequestBody Todo todo) {
        log.info("Updating a TODO with id: {}", id);
        return todoService.updateTodo(id, todo);
    }
}