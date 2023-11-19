package com.example.demo.activation.repository;

import com.example.demo.activation.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Integer> {
    Optional<ActivationToken> findByToken(String token);
}
