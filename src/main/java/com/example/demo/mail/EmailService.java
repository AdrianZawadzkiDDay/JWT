package com.example.demo.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        send("zawadzkii@o2.pl", "Potwierdzenie rejestracji",
                """
                    Misiek fajny kot.
                    Duzo zabaw duzo psot.
                    
                    """);
    }

    public void send(String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("01code.adrian@gmail.com");
        message.setTo(to);
        message.setReplyTo("01code.adrian@gmail.com");
        message.setSubject(subject);
        message.setText(msg);

        System.out.println("przed wysyłką");
        mailSender.send(message);
        System.out.println("Wysłano mail");
    }

}
