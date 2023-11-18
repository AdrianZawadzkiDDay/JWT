package com.example.demo.activation;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.auth.exception.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ActivationController {

    @PostMapping("/activate")
    public void activateAccount() {
        System.out.println("Wywolalo sie z linka");
    }
}
