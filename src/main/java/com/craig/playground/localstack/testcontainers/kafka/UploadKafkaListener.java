package com.craig.playground.localstack.testcontainers.kafka;

import com.craig.playground.localstack.testcontainers.config.properties.S3Properties;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class UploadKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadKafkaListener.class);

    private final S3Template s3Template;
    private final S3Properties s3Properties;

    public UploadKafkaListener(S3Template s3Template, S3Properties s3Properties) {
        this.s3Template = s3Template;
        this.s3Properties = s3Properties;
    }

    @KafkaListener(topics = "test-topic")
    public void handle(ConsumerRecord<String, String> record) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(record.value().getBytes())) {
            LOGGER.info("Uploading data to S3");

            S3Resource uploadedResource = s3Template.upload(s3Properties.getBucket(), record.key(), inputStream);

            LOGGER.info("Uploaded data to S3: {}", uploadedResource.metadata());
        }
    }
}
