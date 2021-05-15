package com.springboot.fullstackwebappdevelopment.service;

import com.springboot.fullstackwebappdevelopment.model.User;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    public String validatePassword(User user, String c_password) {
        String message = "";
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getPassword());
        if(matcher.matches() != true) {
            message = "! Password is not in proper format !";
        }
        if(matcher.matches() == true && c_password.compareTo(user.getPassword()) != 0) {
            message = "! Password does not match !";
        }
        return message;
    }


}
