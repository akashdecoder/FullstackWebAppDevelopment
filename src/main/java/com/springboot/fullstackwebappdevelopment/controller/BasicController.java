package com.springboot.fullstackwebappdevelopment.controller;

import com.springboot.fullstackwebappdevelopment.model.Todo;
import com.springboot.fullstackwebappdevelopment.model.User;
import com.springboot.fullstackwebappdevelopment.repository.TodoRepository;
import com.springboot.fullstackwebappdevelopment.repository.UserRepository;
import com.springboot.fullstackwebappdevelopment.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BasicController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/home")
    public String viewHomePage() {
        return "home.html";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }


    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login.html";
        }
        return "redirect:/";
    }

    @GetMapping("/login_error")
    public String viewLoginErrorPage() {
        return "login_error.html";
    }



    @GetMapping("/logged_out")
    public String loggedOut() {
        return "logged_out.html";
    }


    @GetMapping("/add-todo")
    public String showAddTodoPage(ModelMap model) {
        model.addAttribute("todo", new Todo());
        return "todo";
    }

    @PostMapping("/added-todo")
    public String addTodo(Model model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "todo";
        }
        String email="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        }
        todo.setUsername(email);
        todoService.saveTodo(todo);
        return "redirect:/users";
    }




    @GetMapping("/users")
    public String listUsers(Model model) {
        String email="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        }
        User user = userRepository.findByUserEmail(email);
        model.addAttribute("Users", user);
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        List<Todo> listtodos = todoRepository.findByUsername(email);
        model.addAttribute("listtodos", listtodos);
        List<Todo> listtodosadmin = todoRepository.findAll();
        model.addAttribute("listtodosadmin", listtodosadmin);
        return "users.html";
    }

}
