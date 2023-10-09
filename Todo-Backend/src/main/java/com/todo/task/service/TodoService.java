package com.todo.task.service;

import com.todo.task.exception.TodoNotFoundException;
import com.todo.task.model.Task;
import com.todo.task.model.Todo;
import com.todo.task.repository.TodoRepository;
import com.todo.task.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static com.todo.task.util.TodoConstants.*;


/**
 * Service class for managing Todo entities.
 */
@Service
@Slf4j
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * Create a new Todo.
     *
     * @param todo The Todo object to be created.
     * @return A ResponseEntity containing the created Todo and an appropriate HTTP status code.
     */
    public ResponseEntity<CustomResponse<Todo>> createTodo(Todo todo) {
        CustomResponse<Todo> response;
        HttpStatus httpStatus;
        try {
            Assert.notNull(todo.getName(), NO_NAME_SUPPLIED_IN_THE_PAYLOAD);
            Todo createdTodo = todoRepository.save(todo);
            response = new CustomResponse<>(true, DATA_CREATED, createdTodo);
            httpStatus = HttpStatus.OK;
        } catch (IllegalArgumentException | DataIntegrityViolationException exception) {
            log.error("Error creating data with todo-name:{} with exception: {}", todo.getName(), exception);
            response = new CustomResponse<>(false, INVALID_REQUEST_BODY, null);
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            log.error("Error connecting to the database:", ex);
            response = new CustomResponse<>(false, ERROR_WHILE_SAVING_DATA, null);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Retrieve a Todo by its ID.
     *
     * @param id The ID of the Todo to retrieve.
     * @return A ResponseEntity containing the retrieved Todo if found, or an empty Optional if not found.
     */
    public ResponseEntity<CustomResponse<Todo>> getTodo(Long id) {
        CustomResponse<Todo> response;
        HttpStatus httpStatus;
        try {
            Assert.notNull(id, NOT_ID_SUPPLIED);
            Optional<Todo> todo = Optional.ofNullable(todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Not found")));
            response = new CustomResponse<>(true, DATA_FOUND, todo.get());
            httpStatus = HttpStatus.OK;
        } catch (TodoNotFoundException exception) {
            log.error("Error finding todo with id: {}", id);
            response = new CustomResponse<>(false, TODO_NOT_FOUND, null);
            httpStatus = HttpStatus.NOT_FOUND;
        } catch (Exception ex) {
            log.error("Error connecting to the database:", ex);
            response = new CustomResponse<>(false, ERROR_ACCESSING_DATABASE, null);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Retrieve all Todos.
     *
     * @return A list of all Todos.
     */
    public ResponseEntity<CustomResponse<List<Todo>>> findAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        CustomResponse<List<Todo>> response = new CustomResponse<>(true, DATA_FOUND, todos);
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Delete a Todo by its ID.
     *
     * @param id The ID of the Todo to delete.
     */
    public ResponseEntity<CustomResponse<Todo>> deleteTodo(Long id) {
        Assert.notNull(id, NOT_ID_SUPPLIED);
        CustomResponse<Todo> response;
        HttpStatus httpStatus;
        try {
            todoRepository.deleteById(id);
            response = new CustomResponse<>(true, DATA_DELETED, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            log.error("Error Deleting todo with id: {}", id);
            response = new CustomResponse<>(false, ID_NOT_FOUND, null);
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Update a Todo by its ID.
     *
     * @param id   The ID of the Todo to update.
     * @param todo The updated Todo object.
     * @return A ResponseEntity containing the updated Todo and an appropriate HTTP status code.
     */
    public ResponseEntity<CustomResponse<Todo>> updateTodo(Long id, Todo todo) {
        CustomResponse<Todo> response;
        HttpStatus httpStatus;
        try {
            Assert.notNull(id, NOT_ID_SUPPLIED);
            Todo existingTodo = todoRepository.findById(id)
                    .orElseThrow(() -> new TodoNotFoundException("Todo not found for this id :: " + id));

            // Update the properties of the existing Todo based on the input Todo
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setName(todo.getName());

            // Fetch the associated tasks of the existing Todo
            List<Task> existingTasks = existingTodo.getTasks();

            // Update the existing tasks based on the input Todo's tasks
            for (int i = 0; i < existingTasks.size(); i++) {
                Task existingTask = existingTasks.get(i);
                Task updatedTask = todo.getTasks().get(i);

                // Update the properties of the existing task
                existingTask.setName(updatedTask.getName());
                existingTask.setDescription(updatedTask.getDescription());
            }

            // Save the updated Todo and its associated tasks
            Todo updatedTodo = todoRepository.save(existingTodo);

            response = new CustomResponse<>(true, DATA_UPDATED, updatedTodo);
            httpStatus = HttpStatus.OK;
        } catch (TodoNotFoundException exception) {
            log.error("Error updating todo with id: {}", id);
            response = new CustomResponse<>(false, TODO_NOT_FOUND, null);
            httpStatus = HttpStatus.NOT_FOUND;
        } catch (Exception ex) {
            log.error("Error updating todo with id: {}", id, ex);
            response = new CustomResponse<>(false, ERROR_ACCESSING_DATABASE, null);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}