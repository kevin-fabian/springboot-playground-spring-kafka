package com.fabiankevin.springbootkafkalistenerintegrationtest.listeners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(topics = {"gems"}, partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class GemConsumerTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    void listen_give_when_then() throws InterruptedException {
        kafkaTemplate.send("gems", "Amethyst");
        countDownLatch.await(1, TimeUnit.SECONDS);
    }

    @Test
    void listen_give_when_then2() throws InterruptedException {
        kafkaTemplate.send("gems", "Amethyst");
        countDownLatch.await(1, TimeUnit.SECONDS);
    }
}


