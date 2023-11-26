package com.example.demo.activation.service;

import com.example.demo.activation.ClockFactory;
import com.example.demo.activation.entity.ActivationToken;
import com.example.demo.activation.exception.ActivationTokenExpiredException;
import com.example.demo.activation.repository.ActivationTokenRepository;
import com.example.demo.mail.EmailService;
import com.example.demo.repository.UserRepository;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivationTokenService {

    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    private final ClockFactory clockFactory;


    public void saveActivationToken(String email) {
        String uuid = UUID.randomUUID().toString();

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        log.info("Current Timestamp: " + createdAt);

        LocalDateTime localDateTime = createdAt.toLocalDateTime();
        LocalDateTime localDateTimeExpired = localDateTime.plus(15, ChronoUnit.MINUTES);
        Timestamp expiredAt = Timestamp.valueOf(localDateTimeExpired);

        ActivationToken activationToken = ActivationToken.builder()
                .token(uuid)
                .createdAt(createdAt)
                .expiredAt(expiredAt)
                .userName(email)
                .build();

        activationTokenRepository.save(activationToken);
        String activationURL = "http://localhost:4200/activateAccount?activationCode=" + uuid;
        Context context = new Context();
        context.setVariable("activationLink", activationURL);
        String body = templateEngine.process("email/activation", context);

        log.info("before send mail");
        emailService.send("test@tlen.pl", "Potwierdzenie rejestracji", body);
    }

    @Transactional
    public void activateAccount(String token) throws Exception {
        Optional<ActivationToken> optionalActivationToken = activationTokenRepository.findByToken(token);
        if(optionalActivationToken.isPresent()) {
            ActivationToken activationToken = optionalActivationToken.get();

            Timestamp expiredTimestamp = activationToken.getExpiredAt();
            Instant currentInstant = Instant.now(clockFactory.get());
            Timestamp currentTimestamp = Timestamp.from(currentInstant);
            int comparisonResult = currentTimestamp.compareTo(expiredTimestamp);

            if(comparisonResult > 0) {
                throw new ActivationTokenExpiredException();
            }

            String userName = activationToken.getUserName();

            Optional<User> userOptional = userRepository.findByEmail(userName);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                user.setIsEnabled(true);
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }


}
