package com.craig.playground.localstack.testcontainers;

import org.springframework.boot.SpringApplication;

public class TestLocalstackTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.from(LocalstackTestcontainersApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
