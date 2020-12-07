package com.taner.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taner.todo.model.Todo;
import com.taner.todo.repository.TodoRepository;

@Service
public class TodoService implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodosByUser(String user) {
		
		return todoRepository.findByUsername(user);
	}


	public Optional<Todo> getTodoById(long id) {
		
		return todoRepository.findById(id);
	}

	public void updateTodo(Todo todo) {

		 todoRepository.save(todo);
		
	}

	public void addTodo(String name, String desc, String targetDate) {

		todoRepository.save(new Todo(name, desc, targetDate));
		
	}

	public void deleteTodo(long id) {

		Optional < Todo > todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todoRepository.delete(todo.get());
        }
		
	}

	public void saveTodo(Todo todo) {
		
		todoRepository.save(todo);
		
	}
}