package com.taner.todo.service;

import java.util.List;
import java.util.Optional;

import com.taner.todo.model.Todo;

public interface ITodoService {

    List < Todo > getTodosByUser(String user);

    Optional < Todo > getTodoById(long id);

    void updateTodo(Todo todo);

    void addTodo(String name, String desc, String targetDate);

    void deleteTodo(long id);

    void saveTodo(Todo todo);
}