package com.example.demo.activation;

import java.time.Clock;
import java.util.function.Supplier;

public interface ClockFactory extends Supplier<Clock> {
}
