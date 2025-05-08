package com.craig.playground.localstack.testcontainers;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class LocalstackTestcontainersApplicationTests {

    @Autowired
    private S3Template s3Template;

    @Test
    void contextLoads() throws IOException {
        byte[] bytes = "Hello, World!".getBytes();

        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            s3Template.upload(TestcontainersConfiguration.BUCKET_NAME, "test.txt", inputStream);
        }

        S3Resource downloadedResource = s3Template.download(TestcontainersConfiguration.BUCKET_NAME, "test.txt");

        assertThat(downloadedResource.getInputStream()).hasBinaryContent(bytes);
    }

}
