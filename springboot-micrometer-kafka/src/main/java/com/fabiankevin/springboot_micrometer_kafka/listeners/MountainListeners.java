package com.fabiankevin.springboot_micrometer_kafka.listeners;

import com.fabiankevin.springboot_micrometer_kafka.models.Mountain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class MountainListeners {

    @KafkaListener(topics = "mountains-single", groupId = "mountain-group", containerFactory = "kafkaListenerContainerFactorySingle")
    public void listen(Mountain mountain, ConsumerRecordMetadata meta) throws InterruptedException {
        log.info("Received data: {} topic={}, partition={}, offset={}", mountain, meta.topic(), meta.partition(),  meta.offset());
    }

    @KafkaListener(topics = "mountains", groupId = "mountain-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<Mountain> mountains, Acknowledgment acknowledgment, @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws InterruptedException {
        log.info("Received data: {} topic={}, partition={}, offset={}", mountains.size(), topics, partitions, offsets);

        if(mountains.size() == 3 ){
            throw new RuntimeException("Testing error");
        }
        acknowledgment.acknowledge();
    }
}
