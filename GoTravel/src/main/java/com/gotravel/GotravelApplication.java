package com.gotravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootApplication
public class GotravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(GotravelApplication.class, args);
    }

}
