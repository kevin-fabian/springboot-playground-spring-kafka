package com.fabiankevin.springbootkafkaavroconsumer;

import com.fabiankevin.EmployeeSchema;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldRetryOnlyBlockingException;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldRetryViaBothException;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldSkipBothRetriesException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationSupport;
import org.springframework.kafka.support.serializer.DelegatingByTypeSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaConfig2 extends RetryTopicConfigurationSupport {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, Object> kafkaTemplate) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .fixedBackOff(10000)
                .maxAttempts(5)
                .useSingleTopicForSameIntervals()
                .concurrency(5)
                .autoStartDltHandler(true)
//                .dltHandlerMethod(new EndpointHandlerMethod(DltHandler.class,"handle"))
//                .
//                .autoCreateTopics(true)

//                .doNotRetryOnDltFailure()
//                .dltProcessingFailureStrategy(DltStrategy.FAIL_ON_ERROR)
//                .dltHandlerMethod(SpringbootKafkaAvroConsumerApplication.class, "dlt")
//                .doNotRetryOnDltFailure()
//                .autoStartDltHandler(false)
//                .doNotRetryOnDltFailure()
//                .autoCreateTopics(true)
//                .concurrency(1)
//                .retry
                .includeTopic("employees")
                .retryOn(ShouldRetryViaBothException.class)
//                .notRetryOn(ShouldSkipBothRetriesException.class)

//                .retry
                .create(kafkaTemplate);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {

        Map<String, Object> stringObjectMap = kafkaProperties.getProducer().buildProperties();
        stringObjectMap.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        stringObjectMap.putAll(kafkaProperties.getProperties());
        return new DefaultKafkaProducerFactory<>(stringObjectMap, new StringSerializer(),
                new DelegatingByTypeSerializer(Map.of(byte[].class, new ByteArraySerializer(),
                        EmployeeSchema.class, new KafkaAvroSerializer())));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Override
    protected void configureBlockingRetries(BlockingRetriesConfigurer blockingRetries) {
        blockingRetries.retryOn(ShouldRetryOnlyBlockingException.class, ShouldRetryViaBothException.class)
                .backOff(new FixedBackOff(1000, 5));
    }

    @Override
    protected void manageNonBlockingFatalExceptions(List<Class<? extends Throwable>> nonBlockingRetriesExceptions) {
        nonBlockingRetriesExceptions.add(ShouldSkipBothRetriesException.class);
    }
}
