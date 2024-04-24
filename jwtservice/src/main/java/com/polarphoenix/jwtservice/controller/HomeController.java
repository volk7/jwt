package com.polarphoenix.jwtservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    @GetMapping
    public ResponseEntity<String> sayHello() {
        String jsonString = "{\"message\": \"Hi Home\"}";
        return ResponseEntity.ok(jsonString);
    }
}
