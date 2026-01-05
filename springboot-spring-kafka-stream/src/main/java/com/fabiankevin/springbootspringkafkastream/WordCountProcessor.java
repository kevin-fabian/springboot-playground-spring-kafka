package com.fabiankevin.springbootspringkafkastream;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordCountProcessor {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("input-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        KTable<String, Long> wordCounts = messageStream
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .peek(new ForeachAction<String, String>() {
                    @Override
                    public void apply(String s, String s2) {
                        System.out.println("mapValues s: " + s + " s2: " + s2);
                    }
                })
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .peek(new ForeachAction<String, String>() {
                    @Override
                    public void apply(String s, String s2) {
                        System.out.println("flatMapValues s: " + s + " s2: " + s2);
                    }
                })

                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))

                .count(Materialized.as("counts"));

        wordCounts.toStream().to("output-topic");
    }
}