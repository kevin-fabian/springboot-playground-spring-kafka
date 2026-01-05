package com.fabiankevin.springbootspringkafkastream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Slf4j
@SpringBootTest(classes = SpringbootSpringKafkaStreamApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"input-topic", "output-topic"}, partitions = 1)
class KafkaStreamsApplicationLiveTest {
    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @TempDir
    private static File tempDir;

    private KafkaMessageListenerContainer<Integer, String> consumer;


    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        output.clear();
        createConsumer();
    }

    @Test
    void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {
//        postMessage("test message");
        kafkaTemplate.send("input-topic", "test message");
        startOutputTopicConsumer();
//
//        // assert correct counts from output topic
        assertThat(output.poll(2, MINUTES)).isEqualTo("test:1");
        assertThat(output.poll(2, MINUTES)).isEqualTo("message:1");

        // assert correct count from REST service
//        assertThat(getCountFromRestServiceFor("test")).isEqualTo(1);
//        assertThat(getCountFromRestServiceFor("message")).isEqualTo(1);

//        postMessage("another test message");

        // assert correct counts from output topic
//        assertThat(output.poll(2, MINUTES)).isEqualTo("another:1");
//        assertThat(output.poll(2, MINUTES)).isEqualTo("test:2");
//        assertThat(output.poll(2, MINUTES)).isEqualTo("message:2");
//
//        // assert correct count from REST service
//        assertThat(getCountFromRestServiceFor("another")).isEqualTo(1);
//        assertThat(getCountFromRestServiceFor("test")).isEqualTo(2);
//        assertThat(getCountFromRestServiceFor("message")).isEqualTo(2);
    }

//    private int getCountFromRestServiceFor(String word) {
//        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
//        ResponseEntity<String> response = restTemplate.exchange(
//                "/count/" + word),
//                HttpMethod.GET, entity, String.class
//        );
//        return Integer.parseInt(Objects.requireNonNull(response.getBody()));
//    }


    private void createConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fabian");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);

        // set up the consumer for the word count output
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProperties = new ContainerProperties("output-topic");
        consumer = new KafkaMessageListenerContainer<>(cf, containerProperties);
        consumer.setBeanName("templateTests");

        consumer.setupMessageListener((MessageListener<String, Long>) record -> {
            log.info("Record received: {}", record);
            output.add(record.key() + ":" + record.value());
        });
    }

    private void startOutputTopicConsumer() {
        consumer.start();
    }

//    @DynamicPropertySource
//    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
//        registry.add("spring.kafka.streams.state.dir", tempDir::getAbsolutePath);
//    }
}
