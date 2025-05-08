package com.craig.playground.localstack.testcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    public static final String BUCKET_NAME = "my-bucket";
    public static final String LOCALSTACK_NETWORK_ALIAS = BUCKET_NAME + "." + "localstack";

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer() {
        return new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0"));
    }

    @Bean
    @ServiceConnection
    LocalStackContainer localStackContainer() throws IOException, InterruptedException {
        LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
                .withServices(LocalStackContainer.Service.S3)
                .withNetworkAliases(LOCALSTACK_NETWORK_ALIAS);

        localStackContainer.start();
        localStackContainer.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);

        return localStackContainer;
    }

}
