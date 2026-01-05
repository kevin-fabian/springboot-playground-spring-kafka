package com.fabiankevin.springbootspringkafkastream.input;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class InputScheduler {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void pushInputs() {
        log.info("Sending input...");
        List<String> strings = List.of("hello world", "Hello", "java", "Spring boot", "japan");
        Random random = new Random();
        int i = random.nextInt(5);
        log.info("Generated number {}", i);
        kafkaTemplate.send("input-topic", strings.get(i));
    }
}
