package com.veterok.sensorapi.feign;

import com.veterok.sensorapi.model.dto.Geoname;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "geonamesClient", url = "http://api.geonames.org")
public interface GeonamesClient {
    @GetMapping("/timezoneJSON?lat={lat}&lng={lng}&username={username}")
    Mono<Geoname> getTimezone(@PathVariable("lat") double lat, @PathVariable("lng") double lng, @PathVariable("username") String username);
}
