package com.springboot.fullstackwebappdevelopment.repository;

import com.springboot.fullstackwebappdevelopment.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserCrudRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.userEmail = :userEmail")
    public User getUserByUserEmail(@Param("userEmail") String userEmail);
}
