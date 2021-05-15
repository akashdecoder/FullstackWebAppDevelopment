package com.springboot.fullstackwebappdevelopment.repository;

import com.springboot.fullstackwebappdevelopment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userEmail = ?1")
    public User findByUserEmail(String userEmail);
    public User findByResetPasswordToken(String token);
    public User findByFirstName(String username);
}
