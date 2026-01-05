package com.fabiankevin.springbootkafkaproducer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greetings {
    private String name;
    private String message;
}
