package com.springboot.fullstackwebappdevelopment.repository;

import com.springboot.fullstackwebappdevelopment.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoCrudRepository extends CrudRepository<Todo, Long> {
}
