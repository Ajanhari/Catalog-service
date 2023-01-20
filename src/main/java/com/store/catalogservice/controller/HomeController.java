package com.store.catalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Identifies a class defining handlers for REST/HTTP endpoints
public class HomeController {

    @GetMapping("/") //  Handles GET requests to the root endpoint
    public String getGreeting() {
        return "Welcome to the book catalog!";
    }

}
