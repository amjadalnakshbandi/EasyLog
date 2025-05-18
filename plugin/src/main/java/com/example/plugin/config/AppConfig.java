package com.example.plugin.config;

import com.example.plugin.persistence.order.OrderRepositoryBridge;
import com.example.plugin.persistence.user.UserRepositoryBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.UserService;

@Configuration
public class AppConfig {
    @Bean
    public UserRepositoryBridge userRepositoryBridge() {
        return new UserRepositoryBridge();
    }

    @Bean
    public OrderRepositoryBridge orderRepositoryBridge() {return new OrderRepositoryBridge();}

    @Bean
    public UserService userService() {
        return new UserService();
    }
}