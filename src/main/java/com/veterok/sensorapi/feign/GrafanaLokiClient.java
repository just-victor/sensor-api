package com.veterok.sensorapi.feign;

import com.veterok.sensorapi.feign.dto.StreamsDto;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "grafanaLokiClient", url = "https://logs-prod-006.grafana.net")
public interface GrafanaLokiClient {
    @PostMapping("/loki/api/v1/push")
    Mono<Response> pushLogs(StreamsDto streamsDto);
}
