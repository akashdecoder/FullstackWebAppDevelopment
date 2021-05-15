package com.springboot.fullstackwebappdevelopment.controller;

import com.springboot.fullstackwebappdevelopment.model.Todo;
import com.springboot.fullstackwebappdevelopment.model.User;
import com.springboot.fullstackwebappdevelopment.repository.TodoCrudRepository;
import com.springboot.fullstackwebappdevelopment.repository.UserCrudRepository;
import com.springboot.fullstackwebappdevelopment.service.TodoService;
import com.springboot.fullstackwebappdevelopment.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CrudController {

    @Autowired
    UserCrudRepository userCrudRepository;

    @Autowired
    ValidationService validationService;

    @Autowired
    TodoCrudRepository todoCrudRepository;

    @Autowired
    TodoService todoService;


    @GetMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") long user_id, Model model) {
        User user = userCrudRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("Inavlid User Id:" + user_id));
        userCrudRepository.delete(user);
        return "redirect:/users";
    }


    @GetMapping("/edit/{user_id}")
    public String showUpdateForm(@PathVariable("user_id") long user_id, Model model) {
        User user = userCrudRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + user_id));
        model.addAttribute("user", user);
        return "update-user.html";
    }

    @PostMapping("/update/{user_id}")
    public String updateUser(@PathVariable("user_id") long user_id, @Valid User user, BindingResult result, Model model,
                             HttpServletRequest request) {
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
        userCrudRepository.save(user);
        return "updatemessage.html";
    }

    @GetMapping("/delete_todo/{id}")
    public String deleteTodo(@PathVariable("id") long id, Model model) {
        Todo todo = todoCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inavlid User Id:" + id));
        todoCrudRepository.delete(todo);
        return "redirect:/users";
    }

    @GetMapping("/edit_todo_admin/{id}")
    public String showUpdateTodoFormAdmin(@PathVariable("id") long id, Model model) {
        Todo todo = todoCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inavlid User Id:" + id));
        model.addAttribute("todo", todo);
        return "update-todo.html";
    }

    @GetMapping("/edit_todo/{id}")
    public String showUpdateTodoForm(@PathVariable("id") long id, Model model) {
        Todo todo = todoCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inavlid User Id:" + id));
        model.addAttribute("todo", todo);
        return "update-todo.html";
    }

    @PostMapping("/update_todo_admin/{id}")
    public String updateTodo(@PathVariable("id") long id, @Valid Todo todo, BindingResult result, Model model,
                             HttpServletRequest request) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "todo";
        }
        String email="";
        email = request.getParameter("username");
        todo.setUsername(email);
        todoService.saveTodo(todo);
        return "redirect:/users";
    }

}
