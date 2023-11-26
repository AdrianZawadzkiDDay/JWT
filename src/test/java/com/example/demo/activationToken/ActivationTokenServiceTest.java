package com.example.demo.activationToken;

import com.example.demo.activation.ClockFactory;
import com.example.demo.activation.entity.ActivationToken;
import com.example.demo.activation.exception.ActivationTokenExpiredException;
import com.example.demo.activation.repository.ActivationTokenRepository;
import com.example.demo.activation.service.ActivationTokenService;
import com.example.demo.mail.EmailService;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.thymeleaf.TemplateEngine;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ActivationTokenServiceTest {

    private ActivationTokenRepository activationTokenRepository;
    private UserRepository userRepository;
    private TemplateEngine templateEngine;
    private EmailService emailService;
    private ClockFactory clockFactory;
    private ActivationTokenService activationTokenService;

    @BeforeEach
    void setUp() {
        clockFactory = Mockito.mock(ClockFactory.class);
        activationTokenRepository = Mockito.mock(ActivationTokenRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        templateEngine = Mockito.mock(TemplateEngine.class);
        emailService = Mockito.mock(EmailService.class);

        when(clockFactory.get()).thenReturn(Clock.systemDefaultZone());
        activationTokenService = new ActivationTokenService(activationTokenRepository,
                userRepository, templateEngine, emailService, clockFactory);
    }

    @Test
    public void shouldThrowConfirmPhoneCodeIsExpiredException() {
        String token = UUID.randomUUID().toString();
        Optional<ActivationToken> optionalActivationToken = getActivationToken(token);
        when(activationTokenRepository.findByToken(any())).thenReturn(optionalActivationToken);

        Timestamp expiredAt = optionalActivationToken.get().getExpiredAt();


        setUpClock(expiredAt, 12);
        assertThrows(ActivationTokenExpiredException.class,
                () -> activationTokenService.activateAccount(token));
    }

    private Optional<ActivationToken> getActivationToken(String token) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        LocalDateTime localDateTime = currentTimestamp.toLocalDateTime();
        LocalDateTime updatedLocalDateTime = localDateTime.plus(15, ChronoUnit.MINUTES);
        Timestamp updatedTimestamp = Timestamp.valueOf(updatedLocalDateTime);

        ActivationToken activationToken = ActivationToken.builder()
                .token(token)
                .userName("test@test.pl")
                .createdAt(currentTimestamp)
                .expiredAt(updatedTimestamp)
                .build();

        return Optional.of(activationToken);
    }

    private void setUpClock(Timestamp timestamp, int minutes) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        LocalDateTime updatedLocalDateTime = localDateTime.plus(minutes, ChronoUnit.MINUTES);
        Timestamp updatedTimestamp = Timestamp.valueOf(updatedLocalDateTime);

        when(clockFactory.get()).thenReturn(Clock.fixed(updatedTimestamp.toInstant(), ZoneId.systemDefault()));
    }

//    private void setUpClock(ZonedDateTime data, int hour) {
//        int year = data.getYear();
//        int month = data.getMonthValue();
//        int day = data.getDayOfMonth();
//        int newHour = ZonedDateTime.now().getHour() + hour;
//        int minute = data.getMinute();
//        when(clockFactory.get()).thenReturn(Clock.fixed(Instant.from(ZonedDateTime.of(year,
//                month, day, newHour, minute, 0, 0, ZoneId.systemDefault())), ZoneId.systemDefault()));
//    }
}
