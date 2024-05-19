package com.javaproject.quizapp.controllers;

import com.javaproject.quizapp.models.Admin;
import com.javaproject.quizapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("https://quizzer-flax.vercel.app")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin adminToBeVerified) {
        return adminService.authenticateAdmin(adminToBeVerified);
    }
}
