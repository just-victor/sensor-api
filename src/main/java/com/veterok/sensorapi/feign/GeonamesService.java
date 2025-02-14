package com.veterok.sensorapi.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;
import reactivefeign.ReactiveContract;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;

import java.time.ZoneId;

@Slf4j
@Component
public class GeonamesService {
    private static final String USERNAME = "samilenkov";
    private final GeonamesClient geonamesClient;

    public GeonamesService() {
        this.geonamesClient = WebReactiveFeign.<GeonamesClient>builder()
                .contract(new ReactiveContract(new SpringMvcContract()))
                .target(GeonamesClient.class, "http://api.geonames.org");
    }

    public Mono<ZoneId> getTimezone(double lat, double lng) {
        return geonamesClient.getTimezone(lat, lng, USERNAME)
                .doOnNext(geoname -> log.info("Got timezone: {}", geoname))
                .map(geoname -> ZoneId.of(geoname.getTimezoneId()));
    }
}
