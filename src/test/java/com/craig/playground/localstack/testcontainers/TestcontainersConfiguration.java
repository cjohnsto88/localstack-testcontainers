package com.craig.playground.localstack.testcontainers;

import com.craig.playground.localstack.testcontainers.config.properties.S3Properties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer() {
        return new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0"));
    }

    @Bean
    @ServiceConnection
    LocalStackContainer localStackContainer(S3Properties s3Properties) throws IOException, InterruptedException {
        LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
                .withServices(LocalStackContainer.Service.S3)
                .withNetworkAliases(s3Properties.getBucket() +".localstack");

        localStackContainer.start();
        localStackContainer.execInContainer("awslocal", "s3", "mb", "s3://" + s3Properties.getBucket());

        return localStackContainer;
    }

}
