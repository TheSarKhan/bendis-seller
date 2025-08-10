package com.sarkhan.backend.bendisseller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sarkhan.backend.bendisseller.mapper.seller",
        "com.sarkhan.backend.bendisseller.service"
})
public class BendisSellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BendisSellerApplication.class, args);
    }

}
