package com.harshit.api_gateway.config;

import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public Bucket gatewayRateLimiter() {
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(10)
                        .refillIntervally(10, Duration.ofMinutes(1)))
                .build();
    }
}