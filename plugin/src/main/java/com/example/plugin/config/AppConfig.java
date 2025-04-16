package com.example.plugin.config;

import com.example.plugin.persistence.user.UserRepositoryBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public UserRepositoryBridge userRepositoryBridge() {
        return new UserRepositoryBridge();
    }
}