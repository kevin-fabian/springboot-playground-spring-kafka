package com.fabiankevin.springbootkafkalistenerintegrationtest.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GemConsumer {

    @KafkaListener(topics = {"gems"})
    public void listen(String gem) {
      log.info("Received gem={}", gem);
    }
}
