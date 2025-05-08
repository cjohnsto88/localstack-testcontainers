package com.craig.playground.localstack.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LocalstackTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalstackTestcontainersApplication.class, args);
    }

}
