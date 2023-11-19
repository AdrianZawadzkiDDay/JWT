package com.example.demo.activation.entity;

import jakarta.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ActivationToken {
    @Id
    private String token;
    private String userName;
    private Timestamp createdAt;
    private Timestamp expiredAt;
}
