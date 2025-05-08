package com.craig.playground.localstack.testcontainers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SomeController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SomeController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handle() {
        kafkaTemplate.send("test-topic", "test.txt", "Hello, World!");
    }

}
