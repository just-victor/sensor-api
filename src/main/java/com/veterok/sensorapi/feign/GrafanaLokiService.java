package com.veterok.sensorapi.feign;

import com.veterok.sensorapi.feign.dto.StreamsDto;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;
import reactivefeign.ReactiveContract;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class GrafanaLokiService {
    private final GrafanaLokiClient lokiClient;
    private final static String GRAFANA_USER = "1047559";
    private final static String GRAFANA_LOKI_API_KEY = "??????????";
    private final static String GRAFANA_LOKI_URL = "https://logs-prod-006.grafana.net";

    private static final List<String> AUTH_HEADER = List.of("Basic " + Base64.getEncoder().encodeToString((GRAFANA_USER + ":" + GRAFANA_LOKI_API_KEY).getBytes()));
    private static final List<String> CONTENT_TYPE_HEADER = List.of("application/json");

    public GrafanaLokiService() {
        lokiClient = WebReactiveFeign.<GrafanaLokiClient>builder()
                .contract(new ReactiveContract(new SpringMvcContract()))
                .addRequestInterceptor(reactiveHttpRequest -> {
                    reactiveHttpRequest.headers().put("Authorization", AUTH_HEADER);
                    reactiveHttpRequest.headers().put("Content-Type", CONTENT_TYPE_HEADER);
                    return Mono.just(reactiveHttpRequest);
                })
                .target(GrafanaLokiClient.class, "https://logs-prod-006.grafana.net");
        log.info("Grafana Loki service initialized");
    }

    public Mono<Response> pushLogs(StreamsDto streamsDto) {
        return lokiClient.pushLogs(streamsDto).map(response -> {
            if (response.status() != 200) {
                log.error("Error status: {}", response.status());
                log.error("Error body: {}", response.body());
            }
            log.info("Logs pushed to Grafana Loki");
            return response;
        });
    }

    private Long getUnixEpochInNanoSeconds() {
        return Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() * 1000000;
    }
}
