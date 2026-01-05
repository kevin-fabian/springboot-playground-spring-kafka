package com.fabiankevin.springboot_consumer_batch_kafka.listeners;

import com.fabiankevin.springboot_consumer_batch_kafka.exceptions.ShouldRetryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BatchMessageListener {

    @KafkaListener(topics = "weathers", containerFactory = "kafkaListenerContainerFactory")
    public void listen( List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        System.out.println("Received batch of " + records.size());

        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Key: " + record.key() + ", Value: " + record.value());
            if ("Windyx".equalsIgnoreCase(record.value())) {
                throw new ShouldRetryException();
            }
        }

        ack.acknowledge();  // Manually acknowledge the batch
    }
}
