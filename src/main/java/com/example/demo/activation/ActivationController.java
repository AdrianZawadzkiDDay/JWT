package com.example.demo.activation;

import com.example.demo.activation.service.ActivationTokenService;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.example.demo.mail.EmailService;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.TemplateEngine;

@RestController
@RequestMapping("/api/v1/auth")
public class ActivationController {

    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final ActivationTokenService activationTokenService;

    public ActivationController(EmailService emailService, TemplateEngine templateEngine, ActivationTokenService activationTokenService) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.activationTokenService = activationTokenService;
    }

    @PostMapping("/activate")
    public void activateAccount2() {
        System.out.println("Wywolalo sie z linka");
        Context context = new Context();
        context.setVariable("activationLink", "http://localhost:4200/activateInfo");

        String body = templateEngine.process("email/activation", context);
        emailService.send("zawadzkii@tlen.pl", "Potwierdzenie rejestracji", body);
    }

    @PostMapping("/activate/{token}")
    public ResponseEntity activateAccount(@PathVariable("token") String token) {
        System.out.println("Tutaj jest token: " + token);
        try {
            activationTokenService.activateAccount(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }
}
