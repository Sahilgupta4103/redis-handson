package com.example.redisHandson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class redisApplication {

    public static void main(String[] args) {
        SpringApplication.run(redisApplication.class, args);
    }

}
