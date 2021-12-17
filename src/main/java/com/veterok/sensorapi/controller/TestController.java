package com.veterok.sensorapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
public class TestController {
    @GetMapping
    public Mono<Instant> test() {
        return Mono.just(Instant.now());
    }
}
