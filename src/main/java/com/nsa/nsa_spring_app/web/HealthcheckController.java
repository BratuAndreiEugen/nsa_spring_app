package com.nsa.nsa_spring_app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
@CrossOrigin(origins = "*")
public class HealthcheckController {

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello from Spring");
    }

    @GetMapping("/ping")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok().build();
    }
}
