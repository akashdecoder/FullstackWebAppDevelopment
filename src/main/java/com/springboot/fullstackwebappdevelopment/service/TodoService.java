package com.springboot.fullstackwebappdevelopment.service;

import com.springboot.fullstackwebappdevelopment.model.Todo;
import com.springboot.fullstackwebappdevelopment.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
        todoRepository.save(new Todo(name, desc, targetDate, isDone));
    }

    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(long id) {
        Optional< Todo > todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todoRepository.delete(todo.get());
        }
    }

    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public List<Todo> getTodosByUser(String user) {
        return todoRepository.findByUsername(user);
    }

    public Optional < Todo > getTodoById(long id) {
        return todoRepository.findById(id);
    }
}
