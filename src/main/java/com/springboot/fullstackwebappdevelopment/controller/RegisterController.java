package com.springboot.fullstackwebappdevelopment.controller;

import com.springboot.fullstackwebappdevelopment.model.User;
import com.springboot.fullstackwebappdevelopment.repository.UserRepository;
import com.springboot.fullstackwebappdevelopment.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private AdminRepository adminRepository;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/registered")
    public String registerUser(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        String c_pass = request.getParameter("conf_password");
        String err = validationService.validatePassword(user, c_pass);
        if(!err.isEmpty()) {
            ObjectError error = new ObjectError("validationError", err);
            result.addError(error);
        }
        if(result.hasErrors()) {
            return"/register";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "registrationmessage.html";
    }
}
