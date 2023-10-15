package com.example.epamprojectforcicd.repository;

import com.example.epamprojectforcicd.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<Todo, Long> {
}
