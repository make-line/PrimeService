package com.example.primeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimeServiceApplication.class, args);
    }

}
