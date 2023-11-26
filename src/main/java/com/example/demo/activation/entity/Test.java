package com.example.demo.activation.entity;

import com.example.demo.activation.ClockFactory;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        // Tworzenie obiektu Timestamp na podstawie aktualnego czasu
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Current Timestamp: " + currentTimestamp);

        // Dodanie 15 minut do obecnego Timestamp
        LocalDateTime localDateTime = currentTimestamp.toLocalDateTime();
        LocalDateTime updatedLocalDateTime = localDateTime.plus(15, ChronoUnit.MINUTES);
        Timestamp updatedTimestamp = Timestamp.valueOf(updatedLocalDateTime);

        System.out.println("Updated Timestamp (+15 minutes): " + updatedTimestamp);
        int comparisonResult = currentTimestamp.compareTo(updatedTimestamp);
        System.out.println(comparisonResult);

    }
}
