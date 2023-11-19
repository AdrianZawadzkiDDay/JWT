package com.example.demo.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String to, String title, String content) {
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(to);
            helper.setReplyTo("01code.adrian@gmail.com");
            helper.setFrom("01code.adrian@gmail.com");
            helper.setSubject(title);
            helper.setText(content, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        log.info("przed wysyłką");
        mailSender.send(mail);
        log.info("Wysłano mail");
    }

}
