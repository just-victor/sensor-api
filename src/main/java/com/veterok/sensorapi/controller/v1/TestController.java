package com.veterok.sensorapi.controller.v1;

import com.veterok.sensorapi.feign.GeonamesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.veterok.sensorapi.utils.DateUtils.SENSOR_FORMATTER;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final GeonamesService geonamesService;

    @GetMapping
    public Mono<String> test() {
        log.info("Test request");
        return geonamesService.getTimezone(50.0, 30.0)
                .map(timezone -> SENSOR_FORMATTER.format(Instant.now().atZone(timezone)));
    }
}
