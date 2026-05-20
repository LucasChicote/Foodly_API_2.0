package com.foodly.foodly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodlyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodlyApplication.class, args);
    }
}