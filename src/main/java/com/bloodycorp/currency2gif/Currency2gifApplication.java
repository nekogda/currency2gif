package com.bloodycorp.currency2gif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Currency2gifApplication {
    public static void main(String[] args) {
        SpringApplication.run(Currency2gifApplication.class, args);
    }

}

