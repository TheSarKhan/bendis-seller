package com.sarkhan.backend.bendisseller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.sarkhan.backend.bendisseller",
        "com.sarkhan.backend.bendisseller.mapper.seller",
        "com.sarkhan.backend.bendisseller.service",
        "com.sarkhan.backend.bendisseller.config"
})
public class BendisSellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BendisSellerApplication.class, args);
    }
}

