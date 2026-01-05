package com.fabiankevin.springboot_micrometer_kafka.config;

import com.fabiankevin.springboot_micrometer_kafka.models.Mountain;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfig {

    @Bean
    public KafkaAdmin.NewTopics topics456() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("mountains")
                        .build(),
                TopicBuilder.name("mountains-single")
                        .build());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Mountain> kafkaListenerContainerFactorySingle(ConsumerFactory<String, Mountain> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Mountain> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
//        factory.setli
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Mountain> kafkaListenerContainerFactory(ConsumerFactory<String, Mountain> consumerFactory, DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Mountain> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
//        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);  // Manual acknowledgment

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Mountain> consumerFactory(KafkaProperties kafkaProperties, SslBundles sslBundles){
        Map<String, Object> stringObjectMap = kafkaProperties.getConsumer().buildProperties(sslBundles);
        stringObjectMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new DefaultKafkaConsumerFactory<>(stringObjectMap);
    }

    @Bean
    public DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
        // Configure the backoff strategy and maximum retry attempts
        FixedBackOff fixedBackOff = new FixedBackOff(5000l, 1); //
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, fixedBackOff);

//        errorHandler.set
        // Additional configuration for the error handler if needed
        // errorHandler.setCommitRecovered(true);
        errorHandler.addRetryableExceptions(ListenerExecutionFailedException.class, RuntimeException.class);
//        errorHandler.setCommitRecovered(true);
//        errorHandler.bat
//        errorHandler.handle
        return errorHandler;
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<Object, Object> template) {
        return new DeadLetterPublishingRecoverer(template, (record, exception) -> new TopicPartition("mountains-single", record.partition()));
    }
}
