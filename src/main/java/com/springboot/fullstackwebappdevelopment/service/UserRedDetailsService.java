package com.springboot.fullstackwebappdevelopment.service;


import com.springboot.fullstackwebappdevelopment.model.User;
import com.springboot.fullstackwebappdevelopment.repository.UserRepository;
import com.springboot.fullstackwebappdevelopment.security.UserRedDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRedDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail);
        if(user == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        return new UserRedDetails(user);
    }
}