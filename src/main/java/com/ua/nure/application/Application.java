package com.ua.nure.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ua.nure")
@EnableJpaRepositories("com.ua.nure.model.repository")
@EntityScan(basePackages = "com.ua.nure.model.entity")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(Application.class, args);
    }
}