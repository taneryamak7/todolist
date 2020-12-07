package com.taner.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taner.todo.model.Todo;

public interface TodoRepository extends JpaRepository < Todo, Long > {
    List < Todo > findByUsername(String user);
}