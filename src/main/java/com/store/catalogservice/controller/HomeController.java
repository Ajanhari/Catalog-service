package com.store.catalogservice.controller;

import com.store.catalogservice.config.PolarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Identifies a class defining handlers for REST/HTTP endpoints
public class HomeController {

    private final PolarProperties polarProperties;

    public HomeController(PolarProperties polarProperties) {
        this.polarProperties = polarProperties;
    }

    @GetMapping("/") //  Handles GET requests to the root endpoint
    public String getGreeting() {
        return polarProperties.getGreeting();
    }

}
