package com.homihq.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomiManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomiManagerApplication.class, args);
    }

}
