package com.todo.task.repository;


import com.todo.task.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // You can add custom query methods if needed

}