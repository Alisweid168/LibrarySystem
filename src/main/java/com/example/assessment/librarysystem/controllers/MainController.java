package com.example.assessment.librarysystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Welcome to the Library Management System API"; // Customize the message as needed
    }
}