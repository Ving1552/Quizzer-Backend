package com.javaproject.quizapp.controllers;

import com.javaproject.quizapp.models.User;
import com.javaproject.quizapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("https://quizzer-flax.vercel.app")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userToBeVerified) {
        return userService.authenticateUser(userToBeVerified);
    }
}
